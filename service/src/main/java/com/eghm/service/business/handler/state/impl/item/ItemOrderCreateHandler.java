package com.eghm.service.business.handler.state.impl.item;

import cn.hutool.core.collection.CollUtil;
import com.eghm.constant.CommonConstant;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.RefundType;
import com.eghm.model.Item;
import com.eghm.model.ItemSku;
import com.eghm.model.ItemStore;
import com.eghm.model.Order;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.ItemOrderCreateContext;
import com.eghm.service.business.handler.dto.ItemDTO;
import com.eghm.service.business.handler.dto.ItemOrderPayload;
import com.eghm.service.business.handler.dto.OrderPackage;
import com.eghm.service.business.handler.state.OrderCreateHandler;
import com.eghm.utils.DataUtil;
import com.eghm.utils.TransactionUtil;
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

    private final ItemService itemService;

    private final ItemSkuService itemSkuService;

    private final ItemStoreService itemStoreService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    /**
     * 普通订单下单处理逻辑
     * 说明: 由于普通订单存在购物车概念,在下单时会出现多店铺+多商品同时下单支付,因此需要按店铺进行分组生成多个订单
     * @param context 订单信息
     */
    @Override
    public void doAction(ItemOrderCreateContext context) {
        ItemOrderPayload payload = this.getPayload(context);

        if (this.isHotSell(payload)) {
            log.info("该商品为热销商品,走MQ队列处理");
            // 消息队列在事务之外发送减少事务持有时间
            TransactionUtil.afterCommit(() -> this.queueOrder(context));
            return;
        }
        this.createOrder(context, payload);
    }

    /**
     * 创建零售订单
     * @param context 下单信息
     * @param payload 商品信息
     */
    private void createOrder(ItemOrderCreateContext context, ItemOrderPayload payload) {
        // 购物车商品可能存在多商铺同时下单,按店铺进行分组, 同时按商品和skuId进行排序
        Map<Long, List<OrderPackage>> storeMap = payload.getPackageList().stream().sorted(Comparator.comparing(OrderPackage::getStoreId).thenComparing(OrderPackage::getItemId).thenComparing(OrderPackage::getSkuId)).collect(Collectors.groupingBy(OrderPackage::getStoreId, LinkedHashMap::new, Collectors.toList()));

        List<String> orderList = new ArrayList<>(8);
        // 是否为多店铺下单
        boolean isMultiple = storeMap.size() > 1;
        for (Map.Entry<Long, List<OrderPackage>> entry : storeMap.entrySet()) {
            Map<Long, Integer> skuNumMap = entry.getValue().stream().collect(Collectors.toMap(OrderPackage::getSkuId, aPackage -> -aPackage.getNum()));
            // 更新库存信息
            itemSkuService.updateStock(skuNumMap);

            Order order = this.generateOrder(context.getMemberId(), isMultiple, entry.getValue());

            // 因为entry是按商铺进行分组的,直接取第一个店铺的商户id即可
            order.setMerchantId(entry.getValue().get(0).getItemStore().getMerchantId());
            // 添加主订单
            orderService.save(order);
            // 添加商品订单
            itemOrderService.insert(order.getOrderNo(), entry.getValue());
            // 支持多笔同时支付
            orderList.add(order.getOrderNo());
            // 添加优惠券(待定)
        }
        // 30分钟过期定时任务
        TransactionUtil.afterCommit(() -> orderList.forEach(orderNo -> orderMQService.sendOrderExpireMessage(ExchangeQueue.ITEM_PAY_EXPIRE, orderNo)));
        context.setOrderNo(CollUtil.join(orderList, ","));
    }

    /**
     * 是否为热销商品
     * @param payload 商品信息
     * @return true:热销商品(走队列下单) false:非热销商品
     */
    public boolean isHotSell(ItemOrderPayload payload) {
        return payload.getPackageList().stream().anyMatch(orderPackage -> orderPackage.getItem().getHotSell());
    }

    /**
     * 通过消息队列进行下单
     * @param context 下单信息
     */
    protected void queueOrder(ItemOrderCreateContext context) {
        orderMQService.sendOrderCreateMessage(ExchangeQueue.ITEM_ORDER, context);
    }

    /**
     * 添加主订单信息
     * @param memberId 用户ID
     * @param multiple 是否为多笔订单同时支付
     * @param packageList 商品信息
     */
    private Order generateOrder(Long memberId, boolean multiple, List<OrderPackage> packageList) {
        String orderNo = ProductType.ITEM.generateOrderNo();
        Order order = new Order();
        order.setCoverUrl(this.getFirstCoverUrl(packageList));
        order.setMemberId(memberId);
        order.setOrderNo(orderNo);
        order.setMultiple(multiple);
        order.setRefundType(this.getRefundType(packageList));
        order.setProductType(ProductType.ITEM);
        order.setNum(this.getNum(packageList));
        order.setPayAmount(this.getPayAmount(packageList));
        return order;
    }

    /**
     * 获取sku封面图
     * @param packageList 下单的商品列表
     * @return skuPic 多张逗号分隔
     */
    private String getFirstCoverUrl(List<OrderPackage> packageList) {
        return packageList.stream().map(orderPackage -> orderPackage.getSku().getSkuPic()).collect(Collectors.joining(CommonConstant.SPLIT));
    }

    /**
     * 组装商品下单信息
     * @param context 下单信息
     * @return 商品信息及下单信息
     */
    private ItemOrderPayload getPayload(ItemOrderCreateContext context) {

        // 组装数据,减少后面遍历逻辑
        Set<Long> itemIds = context.getItemList().stream().map(ItemDTO::getItemId).collect(Collectors.toSet());
        Map<Long, Item> itemMap = itemService.getByIdShelveMap(itemIds);
        Set<Long> skuIds = context.getItemList().stream().map(ItemDTO::getSkuId).collect(Collectors.toSet());
        Map<Long, ItemSku> skuMap = itemSkuService.getByIdShelveMap(skuIds);
        List<Long> storeIds = itemMap.values().stream().map(Item::getStoreId).distinct().collect(Collectors.toList());
        Map<Long, ItemStore> storeMap = itemStoreService.selectByIdShelveMap(storeIds);

        List<OrderPackage> packageList = new ArrayList<>();
        OrderPackage orderPackage;
        for (ItemDTO item : context.getItemList()) {
            orderPackage = new OrderPackage();
            orderPackage.setItem(itemMap.get(item.getItemId()));
            orderPackage.setSku(skuMap.get(item.getSkuId()));
            orderPackage.setNum(item.getNum());
            orderPackage.setItemId(item.getItemId());
            orderPackage.setSkuId(item.getSkuId());
            orderPackage.setStoreId(orderPackage.getItem().getStoreId());
            orderPackage.setItemStore(storeMap.get(orderPackage.getStoreId()));
            packageList.add(orderPackage);
        }
        ItemOrderPayload orderDTO = DataUtil.copy(context, ItemOrderPayload.class);
        orderDTO.setPackageList(packageList);
        return orderDTO;
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
        return ItemEvent.CREATE;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
