package com.eghm.service.business.handler.state.impl.item;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.eghm.common.JsonService;
import com.eghm.common.OrderMQService;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.item.express.ExpressFeeCalcDTO;
import com.eghm.dto.business.item.express.ItemCalcDTO;
import com.eghm.dto.business.purchase.LimitSkuRequest;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.ScoreType;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.RefundType;
import com.eghm.exception.BusinessException;
import com.eghm.model.*;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.access.impl.ItemAccessHandler;
import com.eghm.service.business.handler.context.ItemOrderCreateContext;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.dto.ItemDTO;
import com.eghm.service.business.handler.dto.ItemOrderPayload;
import com.eghm.service.business.handler.dto.OrderPackage;
import com.eghm.service.business.handler.state.OrderCreateHandler;
import com.eghm.service.member.MemberAddressService;
import com.eghm.service.member.MemberService;
import com.eghm.utils.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.enums.ErrorCode.*;

/**
 * 注意: 目前优惠券逻辑没有使用, 需要补全逻辑
 *
 * @author 二哥很猛
 * @since 2022/9/5
 */
@Slf4j
@AllArgsConstructor
@Service("itemOrderCreateHandler")
public class ItemOrderCreateHandler implements OrderCreateHandler<ItemOrderCreateContext> {

    private final ItemOrderService itemOrderService;

    private final ItemService itemService;

    private final ItemSkuService itemSkuService;

    private final ItemSpecService itemSpecService;

    private final ItemStoreService itemStoreService;

    private final OrderService orderService;

    private final JsonService jsonService;

    private final OrderMQService orderMQService;

    private final MemberAddressService memberAddressService;

    private final ItemGroupOrderService itemGroupOrderService;

    private final GroupBookingService groupBookingService;

    private final LimitPurchaseItemService limitPurchaseItemService;

    private final MemberService memberService;

    /**
     * 普通订单下单处理逻辑
     * 说明: 由于普通订单存在购物车概念,在下单时会出现多店铺+多商品同时下单支付,因此需要按店铺进行分组生成多个订单
     *
     * @param context 订单信息
     */
    @Override
    public void doAction(ItemOrderCreateContext context) {
        this.before(context);
        if (this.isHotSell(context)) {
            log.info("该商品为热销商品,走MQ队列处理");
            TransactionUtil.afterCommit(() -> this.queueOrder(context));
            return;
        }
        ItemOrderPayload payload = this.getPayload(context);

        List<Order> orderList = this.createOrder(context, payload);

        this.after(context, orderList);
    }

    /**
     * 创建零售订单
     * 1. 多店铺多商品进行下单时,需要将商品按店铺进行分组
     * 2. 依次对每个店铺的商品进行下单(含单规格下单逻辑)
     * 2.1: 计算快递费
     * 2.2: 如果是限时购商品,则计算限时购价格
     * 2.3: 如果是拼团商品,则计算拼团价格
     * 2.4: 如果使用积分,则将积分按顺序分到各个订单中
     * 2.4: 生成主订单
     * 2.5: 生成零售订单
     * 2.6: 扣减sku库存
     * 2.7: 生成拼团订单(如果是的话)
     *
     * @param context 下单信息
     * @param payload 商品信息
     * @return 生成的订单列表
     */
    private List<Order> createOrder(ItemOrderCreateContext context, ItemOrderPayload payload) {
        // 购物车商品可能存在多商铺同时下单,按店铺进行分组, 同时按商品和skuId进行排序
        Map<Long, List<OrderPackage>> storeMap = payload.getPackageList().stream().sorted(Comparator.comparing(OrderPackage::getStoreId).thenComparing(OrderPackage::getItemId).thenComparing(OrderPackage::getSkuId)).collect(Collectors.groupingBy(OrderPackage::getStoreId, LinkedHashMap::new, Collectors.toList()));
        List<Order> orderList = new ArrayList<>(8);
        Integer scoreAmount = context.getScoreAmount();
        // 交易单号预生成(主要针对零元付的情况), 如果不是零元付,后续拉起支付时依旧会重新生成(覆盖当前生成的交易单号)
        String tradeNo = ProductType.ITEM.generateTradeNo();
        for (Map.Entry<Long, List<OrderPackage>> entry : storeMap.entrySet()) {
            Map<Long, Integer> skuExpressMap = this.calcExpressFee(entry.getKey(), payload.getMemberAddress().getCountyId(), entry.getValue());
            int expressAmount = skuExpressMap.values().stream().reduce(Integer::sum).orElse(0);
            // 核心逻辑生成主订单
            Order order = this.generateOrder(context, payload, entry, storeMap.size() > 1, expressAmount, scoreAmount);
            scoreAmount = scoreAmount - order.getScoreAmount();
            order.setTradeNo(tradeNo);
            orderService.save(order);
            // 新增零售订单
            itemOrderService.insert(order.getOrderNo(), context.getMemberId(), entry.getValue(), skuExpressMap);
            // 更新sku库存
            Map<Long, Integer> skuNumMap = entry.getValue().stream().collect(Collectors.toMap(OrderPackage::getSkuId, aPackage -> -aPackage.getNum()));
            itemSkuService.updateStock(skuNumMap);
            // 如果是拼团订单的话 一定是单商品
            itemGroupOrderService.save(context, order, entry.getValue().get(0).getItemId());
            orderList.add(order);
        }
        return orderList;
    }

    /**
     * 是否为热销商品
     *
     * @param context 商品下单信息
     * @return true:热销商品(走队列下单) false:非热销商品
     */
    public boolean isHotSell(ItemOrderCreateContext context) {
        return itemService.containHot(context.getItemList().stream().map(ItemDTO::getItemId).collect(Collectors.toList()));
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
     * 生成主订单信息
     * 1. 生成主订单
     * 2. 计算待支付金额 (限时购, 拼团,积分)
     *
     * @param context     上下文
     * @param payload 商品信息
     * @param entry     下单信息
     * @param multiple      是否为多笔订单同时支付
     * @param expressAmount 快递费
     * @param scoreAmount 可以使用的积分数
     * @return 订单信息
     */
    private Order generateOrder(ItemOrderCreateContext context, ItemOrderPayload payload, Map.Entry<Long, List<OrderPackage>> entry, boolean multiple, int expressAmount, Integer scoreAmount) {
        List<OrderPackage> packageList = entry.getValue();
        Order order = DataUtil.copy(payload, Order.class);
        MemberAddress address = payload.getMemberAddress();
        order.setStoreId(entry.getKey());
        order.setOrderNo(ProductType.ITEM.generateOrderNo());
        order.setProvinceId(address.getProvinceId());
        order.setCityId(address.getCityId());
        order.setCountyId(address.getCountyId());
        order.setDetailAddress(address.getDetailAddress());
        order.setNickName(address.getNickName());
        order.setMobile(address.getMobile());
        order.setCoverUrl(this.getFirstCoverUrl(packageList));
        order.setTitle(this.getTitle(packageList));
        order.setMultiple(multiple);
        // 零售商品只支持审核后退款
        order.setRefundType(RefundType.AUDIT_REFUND);
        order.setProductType(ProductType.ITEM);
        order.setNum(this.getNum(packageList));
        order.setMemberId(context.getMemberId());
        Integer itemAmount = this.getItemAmount(packageList, context);
        order.setAmount(itemAmount);
        order.setBookingNo(context.getBookingNo());
        order.setBookingId(context.getBookingId());
        order.setCreateDate(LocalDate.now());
        order.setCreateMonth(LocalDate.now().format(DateUtil.MIN_FORMAT));
        order.setCreateTime(LocalDateTime.now());
        Integer payAmount = itemAmount + expressAmount;
        if (scoreAmount <= 0) {
            order.setPayAmount(payAmount);
            order.setScoreAmount(0);
        } else {
            // 使用积分
            if (payAmount > scoreAmount) {
                order.setScoreAmount(scoreAmount);
                order.setPayAmount(payAmount - scoreAmount);
            } else {
                order.setScoreAmount(payAmount);
                order.setPayAmount(0);
            }
        }
        order.setLimitId(context.getLimitId());
        order.setMerchantId(entry.getValue().get(0).getItemStore().getMerchantId());
        return order;
    }

    /**
     * 生成订单商品标题(快照), 由于可以购物车下单,因此多个商品同时下单时标题拼接在一起可能会过长, 需要截取
     *
     * @param packageList 商品列表
     * @return 标题信息 逗号分割
     */
    private String getTitle(List<OrderPackage> packageList) {
        String collect = packageList.stream().map(orderPackage -> orderPackage.getItem().getTitle()).collect(Collectors.joining(CommonConstant.COMMA));
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
                coverList.add(aPackage.getItem().getCoverUrl().split(CommonConstant.COMMA)[0]);
            }
        }
        return CollUtil.join(coverList, CommonConstant.COMMA);
    }

    /**
     * 1. 设置上下文所需要的参数
     * 2. 组装数据,减少后面遍历逻辑
     * @param context 下单上下文
     * @return 下单信息
     */
    private ItemOrderPayload getPayload(ItemOrderCreateContext context) {
        MemberAddress memberAddress = memberAddressService.getById(context.getAddressId(), context.getMemberId());
        Set<Long> skuIds = context.getItemList().stream().map(ItemDTO::getSkuId).collect(Collectors.toSet());
        Map<Long, ItemSku> skuMap = itemSkuService.getByIdShelveMap(skuIds);
        List<Long> storeIds = context.getItemMap().values().stream().map(Item::getStoreId).distinct().collect(Collectors.toList());
        Map<Long, ItemStore> storeMap = itemStoreService.selectByIdShelveMap(storeIds);
        Map<Long, ItemSpec> specMap = itemSpecService.getByIdMap(context.getItemMap().keySet());

        List<OrderPackage> packageList = new ArrayList<>();
        OrderPackage orderPackage;
        for (ItemDTO vo : context.getItemList()) {
            orderPackage = new OrderPackage();
            orderPackage.setItem(context.getItemMap().get(vo.getItemId()));
            orderPackage.setSku(skuMap.get(vo.getSkuId()));
            orderPackage.setNum(vo.getNum());
            orderPackage.setItemId(vo.getItemId());
            orderPackage.setSkuId(vo.getSkuId());
            orderPackage.setSpec(specMap.get(this.getSpuId(orderPackage.getSku().getSpecId())));
            orderPackage.setStoreId(orderPackage.getItem().getStoreId());
            orderPackage.setItemStore(storeMap.get(orderPackage.getStoreId()));
            packageList.add(orderPackage);
        }
        ItemOrderPayload orderDTO = DataUtil.copy(context, ItemOrderPayload.class);
        orderDTO.setPackageList(packageList);
        orderDTO.setMemberAddress(memberAddress);
        return orderDTO;
    }

    /**
     * 统计商品总金额(不含快递费)
     *
     * @param packageList 下单信息
     * @return 总金额
     */
    private Integer getItemAmount(List<OrderPackage> packageList, ItemOrderCreateContext context) {
        int sum = 0;
        for (OrderPackage aPackage : packageList) {
            // 最终商品单价计算
            Integer finalPrice = this.checkAndCalcFinalPrice(aPackage, context);
            aPackage.setFinalPrice(finalPrice);
            sum += finalPrice * aPackage.getNum();
        }
        return sum;
    }

    /**
     * 计算最终单价
     * 1. 第一个拼团的人是团长,订单创建时生成拼团单号, 后续的拼团人需要拼团号才能进团下单
     * 2. 如果在拼团中, 拼团过了有效期则自动取消
     *
     * @param aPackage 商品信息
     * @param context context
     * @return 单价
     */
    private Integer checkAndCalcFinalPrice(OrderPackage aPackage, ItemOrderCreateContext context) {
        Long limitId = aPackage.getItem().getLimitId();
        if (limitId != null) {
            LimitPurchaseItem purchaseItem = limitPurchaseItemService.getLimitItem(limitId, aPackage.getItemId());
            if (purchaseItem == null) {
                log.error("该限时购活动不存在 [{}] [{}]", limitId, aPackage.getItemId());
                return aPackage.getSku().getSalePrice();
            }
            if (purchaseItem.getStartTime().isAfter(LocalDateTime.now()) || purchaseItem.getEndTime().isBefore(LocalDateTime.now())) {
                log.error("该限时购商品不在活动时间内 [{}] [{}] [{}] [{}]", aPackage.getItemId(), limitId, purchaseItem.getStartTime(), purchaseItem.getEndTime());
                return aPackage.getSku().getSalePrice();
            }
            List<LimitSkuRequest> skuList = jsonService.fromJsonList(purchaseItem.getSkuValue(), LimitSkuRequest.class);
            Map<Long, LimitSkuRequest> skuMap = skuList.stream().collect(Collectors.toMap(LimitSkuRequest::getSkuId, Function.identity()));
            LimitSkuRequest request = skuMap.get(aPackage.getSkuId());
            if (request == null || request.getDiscountPrice() == null || !aPackage.getSku().getSalePrice().equals(request.getSalePrice())) {
                log.error("该限时购商品不在活动价格范围内 [{}] [{}] [{}] [{}]", aPackage.getItemId(), limitId, aPackage.getSkuId(), purchaseItem.getSkuValue());
                return aPackage.getSku().getSalePrice();
            }
            // 此时才算真正限时购商品
            context.setLimitId(limitId);
            return request.getDiscountPrice();
        }

        // 表示是拼团订单
        if (Boolean.TRUE.equals(context.getGroupBooking())) {
            log.info("开始计算拼团价格 [{}] [{}]", aPackage.getItemId(), aPackage.getSkuId());
            this.checkAndSetBooking(aPackage.getItem().getBookingId(), context);
            GroupBooking selected = groupBookingService.getValidById(aPackage.getItem().getBookingId());
            if (selected.getNum() <= context.getBookingNum()) {
                log.info("拼团人数已经满了 [{}]", aPackage.getItem().getBookingId());
                throw new BusinessException(ITEM_GROUP_COMPLETE);
            }
            context.setExpireTime(selected.getExpireTime());
            return groupBookingService.getFinalPrice(selected.getSkuValue(), aPackage.getSku().getSalePrice(), aPackage.getSkuId());
        }
        return aPackage.getSku().getSalePrice();
    }

    /**
     * 校验并设置拼团信息
     *
     * @param bookingId 拼团id
     * @param context context
     */
    private void checkAndSetBooking(Long bookingId, ItemOrderCreateContext context) {
        if (bookingId == null) {
            throw new BusinessException(ITEM_GROUP_OVER);
        }
        if (StrUtil.isBlank(context.getBookingNo())) {
            context.setBookingId(bookingId);
            context.setBookingNo(StringUtil.encryptNumber(IdUtil.getSnowflakeNextId()));
            context.setBookingNum(0);
            context.setStarter(true);
            log.info("团长创建拼团订单 [{}] [{}]", context.getMemberId(), context.getBookingNo());
            return;
        }
        List<ItemGroupOrder> groupList = itemGroupOrderService.getGroupList(context.getBookingNo(), 0);
        if (CollUtil.isEmpty(groupList)) {
            log.info("团员创建拼团订单,但没有待拼团的订单 [{}]", context.getBookingNo());
            throw new BusinessException(ITEM_GROUP_COMPLETE);
        }
        boolean anyMatch = groupList.stream().anyMatch(item -> item.getMemberId().equals(context.getMemberId()));
        if (anyMatch) {
            log.info("重复参加同一个拼团活动 [{}] [{}]", context.getBookingNo(), context.getMemberId());
            throw new BusinessException(ITEM_GROUP_REPEAT);
        }
        context.setBookingId(bookingId);
        context.setBookingNum(groupList.size());
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
            return Long.parseLong(spuList.split(CommonConstant.COMMA)[0]);
        }
        return null;
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

    /**
     * 前置校验
     * @param context 下单信息
     */
    private void before(ItemOrderCreateContext context) {
        // 积分校验
        if (context.getScoreAmount() > 0) {
            int surplus = context.getScoreAmount() % 100;
            if (surplus > 0) {
                throw new BusinessException(SCORE_INTEGER);
            }
            Member member = memberService.getById(context.getMemberId());
            if (member.getScore() < context.getScoreAmount()) {
                throw new BusinessException(SCORE_NOT_ENOUGH);
            }
        }
        // 拼团订单校验
        if (context.getBookingNo() != null) {
            orderService.checkGroupOrder(context.getBookingNo(), context.getMemberId());
        }
        // 校验限购数量
        Set<Long> itemIds = context.getItemList().stream().map(ItemDTO::getItemId).collect(Collectors.toSet());
        Map<Long, Item> itemMap = itemService.getByIdShelveMap(itemIds);
        for (ItemDTO dto : context.getItemList()) {
            Item item = itemMap.get(dto.getItemId());
            if (item.getQuota() < dto.getNum()) {
                throw new BusinessException(ITEM_CHECK_QUOTA, item.getTitle(), item.getQuota());
            }
        }
        context.setItemMap(itemMap);
    }

    /**
     * 订单创建成功的后置校验
     *
     * @param context 下单信息
     * @param orderList 生成的订单信息
     */
    private void after(ItemOrderCreateContext context, List<Order> orderList) {
        memberService.updateScore(context.getMemberId(), ScoreType.PAY, context.getScoreAmount());
        int realPayAmount = orderList.stream().mapToInt(Order::getPayAmount).sum();
        if (realPayAmount <= 0) {
            String tradeNo = orderList.get(0).getTradeNo();
            log.info("该零售订单可能使用积分或优惠券,属于零元付,不做真实支付 [{}]", tradeNo);
            PayNotifyContext notify = new PayNotifyContext();
            notify.setSuccessTime(LocalDateTime.now());
            notify.setTradeNo(tradeNo);
            // 此次没有采用bean注入的方式获取handler? 因为构造方法注入会产生循环依赖
            SpringContextUtil.getBean(ItemAccessHandler.class).paySuccess(notify);
        } else {
            List<String> noList = orderList.stream().map(Order::getOrderNo).collect(Collectors.toList());
            // 30分钟过期定时任务
            TransactionUtil.afterCommit(() -> orderList.forEach(order -> orderMQService.sendOrderExpireMessage(ExchangeQueue.ITEM_PAY_EXPIRE, order.getOrderNo())));
            context.setOrderNo(CollUtil.join(noList, CommonConstant.COMMA));
        }
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
