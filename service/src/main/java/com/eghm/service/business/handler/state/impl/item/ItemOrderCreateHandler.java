package com.eghm.service.business.handler.state.impl.item;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.item.express.ExpressFeeCalcDTO;
import com.eghm.dto.business.item.express.ItemCalcDTO;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.RefundType;
import com.eghm.exception.BusinessException;
import com.eghm.model.*;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.ItemOrderCreateContext;
import com.eghm.service.business.handler.dto.ItemDTO;
import com.eghm.service.business.handler.dto.ItemOrderPayload;
import com.eghm.service.business.handler.dto.OrderPackage;
import com.eghm.service.business.handler.state.OrderCreateHandler;
import com.eghm.service.common.JsonService;
import com.eghm.service.member.MemberAddressService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.TransactionUtil;
import com.eghm.vo.business.group.GroupOrderVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.eghm.enums.ErrorCode.*;

/**
 * @author 二哥很猛
 * @since 2022/9/5
 */
@Service("itemOrderCreateHandler")
@Slf4j
@AllArgsConstructor
public class ItemOrderCreateHandler implements OrderCreateHandler<ItemOrderCreateContext> {

    private final ItemOrderService itemOrderService;

    private final ItemService itemService;

    private final ItemSkuService itemSkuService;

    private final ItemSpecService itemSpecService;

    private final ItemStoreService itemStoreService;

    private final OrderService orderService;

    private final OrderMQService orderMQService;

    private final MemberAddressService memberAddressService;

    private final ItemGroupOrderService itemGroupOrderService;

    private final GroupBookingService groupBookingService;

    private final JsonService jsonService;

    /**
     * 普通订单下单处理逻辑
     * 说明: 由于普通订单存在购物车概念,在下单时会出现多店铺+多商品同时下单支付,因此需要按店铺进行分组生成多个订单
     *
     * @param context 订单信息
     */
    @Override
    public void doAction(ItemOrderCreateContext context) {
        ItemOrderPayload payload = this.getPayload(context);
        if (this.isHotSell(payload)) {
            log.info("该商品为热销商品,走MQ队列处理");
            TransactionUtil.afterCommit(() -> this.queueOrder(context));
            return;
        }
        this.createOrder(context, payload);
    }

    /**
     * 创建零售订单
     * 1. 多店铺多商品进行下单时,需要将商品按店铺进行分组
     * 2. 依次对每个店铺的商品进行下单(含单规格下单逻辑)
     * 2.1: 计算快递费
     * 2.2: 生成零售订单
     * 2.3: 扣减sku库存
     * 2.4: 生成主订单信息
     * 3. 拼接订单并拉起支付
     *
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

            Map<Long, Integer> skuExpressMap = this.calcExpressFee(entry.getKey(), payload.getCountyId(), entry.getValue());
            String orderNo = ProductType.ITEM.generateOrderNo();
            itemOrderService.insert(orderNo, context.getMemberId(), entry.getValue(), skuExpressMap);

            Map<Long, Integer> skuNumMap = entry.getValue().stream().collect(Collectors.toMap(OrderPackage::getSkuId, aPackage -> -aPackage.getNum()));
            itemSkuService.updateStock(skuNumMap);

            int expressAmount = skuExpressMap.values().stream().reduce(Integer::sum).orElse(0);
            Order order = this.generateOrder(context.getMemberId(), isMultiple, entry.getValue(), expressAmount, payload, context);
            order.setOrderNo(orderNo);
            order.setStoreId(entry.getKey());
            order.setRemark(context.getRemark());
            // 同一家商户merchantId肯定是一样的
            order.setMerchantId(entry.getValue().get(0).getItemStore().getMerchantId());
            orderService.save(order);
            // 如果是拼团订单的话 一定是单商品
            itemGroupOrderService.save(context, order, entry.getValue().get(0).getItemId());
            orderList.add(order.getOrderNo());
        }
        // 30分钟过期定时任务
        TransactionUtil.afterCommit(() -> orderList.forEach(orderNo -> orderMQService.sendOrderExpireMessage(ExchangeQueue.ITEM_PAY_EXPIRE, orderNo)));
        context.setOrderNo(CollUtil.join(orderList, ","));
    }

    /**
     * 是否为热销商品
     *
     * @param payload 商品信息
     * @return true:热销商品(走队列下单) false:非热销商品
     */
    public boolean isHotSell(ItemOrderPayload payload) {
        return payload.getPackageList().stream().anyMatch(orderPackage -> orderPackage.getItem().getHotSell());
    }

    /**
     * 通过消息队列进行下单
     *
     * @param context 下单信息
     */
    protected void queueOrder(ItemOrderCreateContext context) {
        orderMQService.sendOrderCreateMessage(ExchangeQueue.ITEM_ORDER, context);
    }

    /**
     * 添加主订单信息
     *
     * @param memberId      用户ID
     * @param multiple      是否为多笔订单同时支付
     * @param packageList   商品信息
     * @param expressAmount 快递费
     * @return 订单信息
     */
    private Order generateOrder(Long memberId, boolean multiple, List<OrderPackage> packageList, int expressAmount, ItemOrderPayload payload, ItemOrderCreateContext context) {
        Order order = DataUtil.copy(payload, Order.class);
        order.setCoverUrl(this.getFirstCoverUrl(packageList));
        order.setTitle(this.getTitle(packageList));
        order.setMemberId(memberId);
        order.setMultiple(multiple);
        order.setRefundType(this.getRefundType(packageList));
        order.setProductType(ProductType.ITEM);
        order.setNum(this.getNum(packageList));
        Integer payAmount = this.getPayAmount(packageList, context);
        order.setBookingNo(context.getBookingNo());
        order.setPayAmount(payAmount + expressAmount);
        return order;
    }

    /**
     * 生成订单商品标题(快照), 由于可以购物车下单,因此多个商品同时下单时标题拼接在一起可能会过长, 需要截取
     *
     * @param packageList 商品列表
     * @return 标题信息 逗号分割
     */
    private String getTitle(List<OrderPackage> packageList) {
        String collect = packageList.stream().map(orderPackage -> orderPackage.getItem().getTitle()).collect(Collectors.joining(CommonConstant.DOT_SPLIT));
        if (collect.length() > 100) {
            return collect.substring(0, 100) + "等";
        }
        return collect;
    }

    /**
     * 获取sku封面图, 注意:单规格sku由于没有封面图,默认取商品的第一张作为封面图
     *
     * @param packageList 下单的商品列表
     * @return skuPic 多张逗号分隔
     */
    private String getFirstCoverUrl(List<OrderPackage> packageList) {
        List<String> coverList = new ArrayList<>(8);
        for (OrderPackage aPackage : packageList) {
            String skuPic = aPackage.getSku().getSkuPic();
            if (skuPic != null) {
                coverList.add(skuPic);
            } else {
                coverList.add(aPackage.getItem().getCoverUrl().split(CommonConstant.DOT_SPLIT)[0]);
            }
        }
        return CollUtil.join(coverList, CommonConstant.DOT_SPLIT);
    }

    /**
     * 组装商品下单信息
     *
     * @param context 下单信息
     * @return 商品信息及下单信息
     */
    private ItemOrderPayload getPayload(ItemOrderCreateContext context) {
        MemberAddress memberAddress = memberAddressService.getById(context.getAddressId(), context.getMemberId());
        // 组装数据,减少后面遍历逻辑
        Set<Long> itemIds = context.getItemList().stream().map(ItemDTO::getItemId).collect(Collectors.toSet());
        Map<Long, Item> itemMap = itemService.getByIdShelveMap(itemIds);
        Set<Long> skuIds = context.getItemList().stream().map(ItemDTO::getSkuId).collect(Collectors.toSet());
        Map<Long, ItemSku> skuMap = itemSkuService.getByIdShelveMap(skuIds);
        List<Long> storeIds = itemMap.values().stream().map(Item::getStoreId).distinct().collect(Collectors.toList());
        Map<Long, ItemStore> storeMap = itemStoreService.selectByIdShelveMap(storeIds);
        Map<Long, ItemSpec> specMap = itemSpecService.getByIdMap(itemIds);

        List<OrderPackage> packageList = new ArrayList<>();
        OrderPackage orderPackage;
        for (ItemDTO vo : context.getItemList()) {
            orderPackage = new OrderPackage();
            orderPackage.setItem(itemMap.get(vo.getItemId()));
            orderPackage.setSku(skuMap.get(vo.getSkuId()));
            orderPackage.setNum(vo.getNum());
            orderPackage.setItemId(vo.getItemId());
            orderPackage.setSkuId(vo.getSkuId());
            orderPackage.setSpec(specMap.get(this.getSpuId(orderPackage.getSku().getSpecId())));
            orderPackage.setStoreId(orderPackage.getItem().getStoreId());
            orderPackage.setItemStore(storeMap.get(orderPackage.getStoreId()));
            this.checkQuota(orderPackage);
            packageList.add(orderPackage);
        }
        ItemOrderPayload orderDTO = DataUtil.copy(context, ItemOrderPayload.class);
        orderDTO.setPackageList(packageList);
        orderDTO.setProvinceId(memberAddress.getProvinceId());
        orderDTO.setCityId(memberAddress.getCityId());
        orderDTO.setCountyId(memberAddress.getCountyId());
        orderDTO.setDetailAddress(memberAddress.getDetailAddress());
        orderDTO.setNickName(memberAddress.getNickName());
        orderDTO.setMobile(memberAddress.getMobile());
        return orderDTO;
    }

    /**
     * 主订单下的商品但凡有一个支持退款,主订单就支持退款,且默认审核后退款
     *
     * @param packageList 下单信息
     * @return 退款方式, 普通商品只支持审核下单或不退款
     */
    private RefundType getRefundType(List<OrderPackage> packageList) {
        Optional<OrderPackage> optional = packageList.stream().filter(orderPackage -> orderPackage.getItem().getRefundType() != RefundType.NOT_SUPPORTED).findFirst();
        return optional.isPresent() ? RefundType.AUDIT_REFUND : RefundType.NOT_SUPPORTED;
    }

    /**
     * 统计总金额
     *
     * @param packageList 下单信息
     * @return 总金额
     */
    private Integer getPayAmount(List<OrderPackage> packageList, ItemOrderCreateContext context) {
        int sum = 0;
        for (OrderPackage aPackage : packageList) {
            Integer finalPrice = this.getFinalPrice(aPackage, context);
            sum += finalPrice * aPackage.getNum();
        }
        return sum;
    }

    /**
     * 计算最终单价
     * 1. 第一个拼团的人是团长,订单创建时生成拼团单号, 后续的拼团人需要拼团号才能进团下单
     * 2. 如果在拼团中, 拼团活动取消了, 之前拼团中还可以继续拼团
     *
     * @param aPackage 商品信息
     * @param context context
     * @return 单价
     */
    private Integer getFinalPrice(OrderPackage aPackage, ItemOrderCreateContext context) {
        // 表示是拼团订单
        if (Boolean.TRUE.equals(context.getGroupBooking())) {
            log.info("开始计算拼团价格 [{}] [{}]", aPackage.getItemId(), aPackage.getSkuId());
            Long bookingId = this.checkAndGetBookingId(aPackage.getItem().getBookingId(), context);
            GroupBooking selected = groupBookingService.getById(bookingId);
            if (selected.getNum() <= context.getBookingNum()) {
                log.info("拼团人数已经满了 [{}]", bookingId);
                throw new BusinessException(ITEM_GROUP_COMPLETE);
            }
            return groupBookingService.getFinalPrice(selected.getSkuValue(), aPackage.getSku().getSalePrice(), aPackage.getSkuId());
        }
        return aPackage.getSku().getSalePrice();
    }

    /**
     * 校验并查询最终拼团id
     *
     * @param bookingId 拼团id
     * @param context context
     * @return 拼团id
     */
    private Long checkAndGetBookingId(Long bookingId, ItemOrderCreateContext context) {
        if (bookingId != null) {
            if (StrUtil.isBlank(context.getBookingNo())) {
                context.setBookingNo(IdWorker.get32UUID());
                context.setBookingId(bookingId);
                log.info("团长还是创建拼团订单 [{}]", context.getBookingNo());
                return bookingId;
            }
            GroupOrderVO vo = itemGroupOrderService.getGroupOrder(context.getBookingNo(), 0);
            if (vo.getNum() == 0) {
                log.info("团员创建拼团订单,但没有待拼团的订单 [{}]", context.getBookingNo());
                throw new BusinessException(ITEM_GROUP_COMPLETE);
            }
            context.setBookingNum(vo.getNum());
            context.setBookingId(bookingId);
            return bookingId;
        }
        if (StrUtil.isBlank(context.getBookingNo())) {
            throw new BusinessException(ITEM_GROUP_NULL);
        }
        GroupOrderVO vo = itemGroupOrderService.getGroupOrder(context.getBookingNo(), 0);
        if (vo.getNum() == 0) {
            log.info("团员创建拼团订单,订单可能已经拼团成功 [{}]", context.getBookingNo());
            throw new BusinessException(ITEM_GROUP_COMPLETE);
        }
        context.setBookingNum(vo.getNum());
        context.setBookingId(vo.getBookingId());
        return vo.getBookingId();
    }


    /**
     * 商品总数量
     *
     * @param packageList 下单商品
     * @return 总数量
     */
    private Integer getNum(List<OrderPackage> packageList) {
        return packageList.stream().mapToInt(OrderPackage::getNum).sum();
    }

    /**
     * 查询sku中的一级spuId
     * @param spuList spuList,多个逗号分隔
     * @return 列表
     */
    private Long getSpuId(String spuList) {
        if (StrUtil.isNotBlank(spuList)) {
            return Long.parseLong(spuList.split(CommonConstant.DOT_SPLIT)[0]);
        }
        return null;
    }

    /**
     * 检查限购数量
     *
     * @param aPackage 零售sku
     */
    private void checkQuota(OrderPackage aPackage) {
        Integer quota = aPackage.getItem().getQuota();
        if (quota < aPackage.getNum()) {
            throw new BusinessException(ITEM_CHECK_QUOTA.getCode(), String.format(ITEM_CHECK_QUOTA.getMsg(), aPackage.getItem().getTitle(), quota));
        }
    }

    /**
     * 计算每个sku商品快递费用
     *
     * @param storeId  店铺名称
     * @param countyId 收货县区
     * @param itemList 下单商品(单个店铺所有商品)
     * @return sku-express 单位:分
     */
    private Map<Long, Integer> calcExpressFee(Long storeId, Long countyId, List<OrderPackage> itemList) {
        ExpressFeeCalcDTO dto = new ExpressFeeCalcDTO();
        List<ItemCalcDTO> dtoList = DataUtil.copy(itemList, ItemCalcDTO.class);
        dto.setOrderList(dtoList);
        dto.setStoreId(storeId);
        dto.setCountyId(countyId);
        itemService.calcStoreExpressFee(dto);
        return dtoList.stream().collect(Collectors.toMap(ItemCalcDTO::getSkuId, ItemCalcDTO::getExpressFee));
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
