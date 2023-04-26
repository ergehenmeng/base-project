package com.eghm.service.business.handler.impl.item;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.RefundType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Item;
import com.eghm.model.ItemSku;
import com.eghm.model.Order;
import com.eghm.model.ShippingAddress;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.OrderCreateHandler;
import com.eghm.service.business.handler.dto.BaseItemDTO;
import com.eghm.service.business.handler.context.ItemOrderCreateContext;
import com.eghm.service.business.handler.dto.ItemOrderPayload;
import com.eghm.service.business.handler.dto.OrderPackage;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2022/9/5
 */
@Service("itemOrderCreateHandler")
@Slf4j
@AllArgsConstructor
public class ItemOrderCreateHandler implements OrderCreateHandler<ItemOrderCreateContext> {

    private final ItemOrderService itemOrderService;

    private final ShippingAddressService shippingAddressService;

    private final ItemService itemService;

    private final ItemSkuService itemSkuService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    /**
     * 普通订单下单处理逻辑
     * 说明: 由于普通订单存在购物车概念,在下单时会出现多店铺+多商品同时下单支付,因此需要按店铺进行分组生成多个订单
     * @param dto 订单信息
     */
    @Override
    public void doAction(ItemOrderCreateContext dto) {
        ItemOrderPayload payload = this.getItem(dto);
        this.before(payload);
        // 购物车商品可能存在多商铺同时下单,按店铺进行分组
        Map<Long, List<OrderPackage>> storeMap = payload.getPackageList().stream().collect(Collectors.groupingBy(OrderPackage::getStoreId, Collectors.toList()));
        // 优惠券还没想好如何设计
        ShippingAddress address = DataUtil.copy(payload, ShippingAddress.class);
        address.setUserId(dto.getUserId());

        for (Map.Entry<Long, List<OrderPackage>> entry : storeMap.entrySet()) {
            Map<Long, Integer> skuNumMap = entry.getValue().stream().collect(Collectors.toMap(OrderPackage::getSkuId, aPackage -> -aPackage.getNum()));
            // 更新库存信息
            itemSkuService.updateStock(skuNumMap);

            String orderNo = ProductType.ITEM.generateOrderNo();
            address.setOrderNo(orderNo);
            address.setId(null);
            Order order = new Order();
            order.setUserId(dto.getUserId());
            order.setOrderNo(orderNo);
            order.setMultiple(true);
            order.setRefundType(this.getRefundType(entry.getValue()));
            order.setProductType(ProductType.ITEM);
            order.setNum(this.getNum(entry.getValue()));
            order.setPayAmount(this.getPayAmount(entry.getValue()));
            // 添加主订单
            orderService.insert(order);
            // 添加配送地址
            shippingAddressService.insert(address);
            // 添加商品订单
            itemOrderService.insert(orderNo, entry.getValue());

            // 30分钟过期定时任务
            orderMQService.sendOrderExpireMessage(orderNo);
            // 添加优惠券
        }
    }


    /**
     * 组装商品下单信息
     * @param dto 下单信息
     * @return 商品信息及下单信息
     */
    private ItemOrderPayload getItem(ItemOrderCreateContext dto) {
        // 组装数据,减少后面遍历逻辑
        Set<Long> itemIds = dto.getItemList().stream().map(BaseItemDTO::getItemId).collect(Collectors.toSet());
        Map<Long, Item> itemMap = itemService.getByIds(itemIds);
        Set<Long> skuIds = dto.getItemList().stream().map(BaseItemDTO::getSkuId).collect(Collectors.toSet());
        Map<Long, ItemSku> skuMap = itemSkuService.getByIds(skuIds);
        List<OrderPackage> packageList = new ArrayList<>();
        OrderPackage orderPackage;
        for (BaseItemDTO item : dto.getItemList()) {
            orderPackage = new OrderPackage();
            orderPackage.setItem(itemMap.get(item.getItemId()));
            orderPackage.setSku(skuMap.get(item.getSkuId()));
            orderPackage.setNum(item.getNum());
            orderPackage.setItemId(item.getItemId());
            orderPackage.setSkuId(item.getSkuId());
            orderPackage.setStoreId(orderPackage.getStoreId());
            packageList.add(orderPackage);
        }
        ItemOrderPayload orderDTO = DataUtil.copy(dto, ItemOrderPayload.class);
        orderDTO.setPackageList(packageList);
        return orderDTO;
    }


    /**
     * 校验下单信息是否合法
     * @param payload 下单信息
     */
    private void before(ItemOrderPayload payload) {
        List<OrderPackage> packageList = payload.getPackageList();
        for (OrderPackage aPackage : packageList) {
            if (aPackage.getItem() == null) {
                log.error("未查询到商品信息 [{}] ", aPackage.getItemId());
                throw new BusinessException(ErrorCode.ITEM_DOWN);
            }
            ItemSku sku = aPackage.getSku();
            if (sku == null) {
                log.error("未查询到商品规格信息 [{}] ", aPackage.getSkuId());
                throw new BusinessException(ErrorCode.SKU_STOCK);
            }
            if (!sku.getItemId().equals(aPackage.getItemId())) {
                log.error("下单商品和规格不匹配 [{}] [{}]", aPackage.getSkuId(), aPackage.getItemId());
                throw new BusinessException(ErrorCode.ITEM_SKU_MATCH);
            }
            if (sku.getStock() < aPackage.getNum()) {
                log.error("商品规格库存不足 [{}] [{}] [{}]", sku.getId(), sku.getStock(), aPackage.getNum());
                throw new BusinessException(ErrorCode.SKU_STOCK);
            }
        }
    }

    /**
     * 主订单下的商品但凡有一个支持退款,主订单就支持退款,且默认审核后退款
     * @param packageList 下单信息
     * @return 退款方式, 普通商品只支持审核下单或不退款
     */
    private RefundType getRefundType(List<OrderPackage> packageList) {
        Optional<OrderPackage> optional = packageList.stream().filter(orderPackage -> orderPackage.getItem().getRefundType() != RefundType.NOT_SUPPORTED).findFirst();
        return optional.isPresent() ? RefundType.AUDIT_REFUND : RefundType.NOT_SUPPORTED;
    }

    /**
     * 统计总金额
     * @param packageList 下单信息
     * @return 总金额
     */
    private Integer getPayAmount(List<OrderPackage> packageList) {
        return packageList.stream().mapToInt(orderPackage -> orderPackage.getSku().getSalePrice() * orderPackage.getNum()).sum();
    }

    /**
     * 商品总数量
     * @param packageList 下单商品
     * @return 总数量
     */
    private Integer getNum(List<OrderPackage> packageList) {
        return packageList.stream().mapToInt(OrderPackage::getNum).sum();
    }

    @Override
    public IEvent getEvent() {
        return null;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
