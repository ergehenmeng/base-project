package com.eghm.web.listener;

import cn.hutool.core.util.StrUtil;
import com.eghm.cache.CacheService;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.constant.CacheConstant;
import com.eghm.constant.CommonConstant;
import com.eghm.constant.LockKey;
import com.eghm.constant.QueueConstant;
import com.eghm.dto.ext.*;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.*;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.lock.RedisLock;
import com.eghm.model.*;
import com.eghm.mq.listener.AbstractListenerHandler;
import com.eghm.service.business.*;
import com.eghm.service.member.LoginService;
import com.eghm.service.sys.WebappLogService;
import com.eghm.state.machine.StateHandler;
import com.eghm.state.machine.context.*;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.group.GroupOrderCancelVO;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import static com.eghm.constant.CacheConstant.ERROR_PLACE_HOLDER;
import static com.eghm.constant.CacheConstant.SUCCESS_PLACE_HOLDER;

/**
 * @author 二哥很猛
 * @since 2022/7/28
 */
@Component
@Slf4j
public class WebappListenerHandler extends AbstractListenerHandler {

    private final RedisLock redisLock;

    private final LineService lineService;

    private final JsonService jsonService;

    private final ItemService itemService;

    private final LoginService loginService;

    private final CacheService cacheService;

    private final StateHandler stateHandler;

    private final OrderService orderService;

    private final VenueService venueService;

    private final HomestayService homestayService;

    private final WebappLogService webappLogService;

    private final OrderProxyService orderProxyService;

    private final RestaurantService restaurantService;

    private final ScenicTicketService scenicTicketService;

    private final GroupBookingService groupBookingService;

    private final ItemGroupOrderService itemGroupOrderService;

    private final MemberVisitLogService memberVisitLogService;

    private final OrderEvaluationService orderEvaluationService;

    public WebappListenerHandler(JsonService jsonService, AlarmService alarmService, RedisLock redisLock, LineService lineService, JsonService jsonService1, ItemService itemService, LoginService loginService, CacheService cacheService, StateHandler stateHandler, OrderService orderService, VenueService venueService, HomestayService homestayService, WebappLogService webappLogService, OrderProxyService orderProxyService, RestaurantService restaurantService, ScenicTicketService scenicTicketService, GroupBookingService groupBookingService, ItemGroupOrderService itemGroupOrderService, MemberVisitLogService memberVisitLogService, OrderEvaluationService orderEvaluationService) {
        super(jsonService, alarmService);
        this.redisLock = redisLock;
        this.lineService = lineService;
        this.jsonService = jsonService1;
        this.itemService = itemService;
        this.loginService = loginService;
        this.cacheService = cacheService;
        this.stateHandler = stateHandler;
        this.orderService = orderService;
        this.venueService = venueService;
        this.homestayService = homestayService;
        this.webappLogService = webappLogService;
        this.orderProxyService = orderProxyService;
        this.restaurantService = restaurantService;
        this.scenicTicketService = scenicTicketService;
        this.groupBookingService = groupBookingService;
        this.itemGroupOrderService = itemGroupOrderService;
        this.memberVisitLogService = memberVisitLogService;
        this.orderEvaluationService = orderEvaluationService;
    }

    /**
     * 零售商品消息队列订单过期处理
     *
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.ITEM_PAY_EXPIRE_QUEUE)
    public void itemExpire(String orderNo, Message message, Channel channel) throws IOException {
        this.doOrderExpire(orderNo, ItemEvent.AUTO_CANCEL, message, channel);
    }

    /**
     * 门票队列订单过期处理
     *
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.TICKET_PAY_EXPIRE_QUEUE)
    public void ticketExpire(String orderNo, Message message, Channel channel) throws IOException {
        this.doOrderExpire(orderNo, TicketEvent.AUTO_CANCEL, message, channel);
    }

    /**
     * 场馆队列订单过期处理
     *
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.VENUE_PAY_EXPIRE_QUEUE)
    public void venueExpire(String orderNo, Message message, Channel channel) throws IOException {
        this.doOrderExpire(orderNo, VenueEvent.AUTO_CANCEL, message, channel);
    }

    /**
     * 民宿队列订单过期处理
     *
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.HOMESTAY_PAY_EXPIRE_QUEUE)
    public void homeStayExpire(String orderNo, Message message, Channel channel) throws IOException {
        this.doOrderExpire(orderNo, HomestayEvent.AUTO_CANCEL, message, channel);
    }

    /**
     * 餐饮券队列订单过期处理
     *
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.RESTAURANT_PAY_EXPIRE_QUEUE)
    public void voucherExpire(String orderNo, Message message, Channel channel) throws IOException {
        this.doOrderExpire(orderNo, VoucherEvent.AUTO_CANCEL, message, channel);
    }

    /**
     * 线路队列订单过期处理
     *
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.LINE_PAY_EXPIRE_QUEUE)
    public void lineExpire(String orderNo, Message message, Channel channel) throws IOException {
        this.doOrderExpire(orderNo, LineEvent.AUTO_CANCEL, message, channel);
    }

    /**
     * 移动端操作日志
     */
    @RabbitListener(queues = QueueConstant.WEBAPP_LOG_QUEUE)
    public void webappLog(WebappLog webappLog, Message message, Channel channel) throws IOException {
        processMessageAck(webappLog, message, channel, webappLogService::insertWebappLog);
    }

    /**
     * 移动端登陆日志
     */
    @RabbitListener(queues = QueueConstant.LOGIN_LOG_QUEUE)
    public void loginLog(LoginRecord loginRecord, Message message, Channel channel) throws IOException {
        processMessageAck(loginRecord, message, channel, loginService::insertLoginLog);
    }

    /**
     * 门票下单
     *
     * @param context 下单信息
     */
    @RabbitListener(queues = QueueConstant.TICKET_ORDER_QUEUE)
    public void ticketOrder(TicketOrderCreateContext context, Message message, Channel channel) throws IOException {
        this.processMessageAckAsync(context, message, channel, order -> {
            stateHandler.fireEvent(ProductType.TICKET, OrderState.NONE.getValue(), TicketEvent.CREATE_QUEUE, context);
            cacheService.setValue(CacheConstant.MQ_ASYNC_DATA_KEY + context.getKey(), context.getOrderNo());
        });
    }

    /**
     * 零售下单
     *
     * @param context 下单信息
     */
    @RabbitListener(queues = QueueConstant.ITEM_ORDER_QUEUE)
    public void itemOrder(ItemOrderCreateContext context, Message message, Channel channel) throws IOException {
        this.processMessageAckAsync(context, message, channel, order -> {
            stateHandler.fireEvent(ProductType.ITEM, OrderState.NONE.getValue(), ItemEvent.CREATE_QUEUE, context);
            cacheService.setValue(CacheConstant.MQ_ASYNC_DATA_KEY + context.getKey(), context.getOrderNo());
        });
    }

    /**
     * 线路下单
     *
     * @param context 下单信息
     */
    @RabbitListener(queues = QueueConstant.LINE_ORDER_QUEUE)
    public void lineOrder(LineOrderCreateContext context, Message message, Channel channel) throws IOException {
        this.processMessageAckAsync(context, message, channel, order -> {
            stateHandler.fireEvent(ProductType.LINE, OrderState.NONE.getValue(), LineEvent.CREATE_QUEUE, context);
            cacheService.setValue(CacheConstant.MQ_ASYNC_DATA_KEY + context.getKey(), context.getOrderNo());
        });
    }

    /**
     * 民宿下单
     *
     * @param context 下单信息
     */
    @RabbitListener(queues = QueueConstant.HOMESTAY_ORDER_QUEUE)
    public void homestayOrder(HomestayOrderCreateContext context, Message message, Channel channel) throws IOException {
        this.processMessageAckAsync(context, message, channel, order -> {
            stateHandler.fireEvent(ProductType.HOMESTAY, OrderState.NONE.getValue(), HomestayEvent.CREATE_QUEUE, context);
            cacheService.setValue(CacheConstant.MQ_ASYNC_DATA_KEY + context.getKey(), context.getOrderNo());
        });
    }

    /**
     * 餐饮券下单
     *
     * @param context 下单信息
     */
    @RabbitListener(queues = QueueConstant.VOUCHER_ORDER_QUEUE)
    public void voucherOrder(VoucherOrderCreateContext context, Message message, Channel channel) throws IOException {
        this.processMessageAckAsync(context, message, channel, order -> {
            stateHandler.fireEvent(ProductType.VOUCHER, OrderState.NONE.getValue(), VoucherEvent.CREATE_QUEUE, context);
            cacheService.setValue(CacheConstant.MQ_ASYNC_DATA_KEY + context.getKey(), context.getOrderNo());
        });
    }

    /**
     * 更新分数
     *
     * @param vo vo
     */
    @RabbitListener(queues = QueueConstant.PRODUCT_SCORE_QUEUE)
    public void updateProductScore(CalcStatistics vo, Message message, Channel channel) throws IOException {
        processMessageAck(vo, message, channel, msg -> {
            log.info("更新商品分数信息 [{}]", vo);
            switch (vo.getProductType()) {
                case TICKET:
                    scenicTicketService.updateScore(vo);
                    break;
                case ITEM:
                    itemService.updateScore(vo);
                    break;
                case LINE:
                    lineService.updateScore(vo);
                    break;
                case HOMESTAY:
                    homestayService.updateScore(vo);
                    break;
                case VOUCHER:
                    restaurantService.updateScore(vo);
                    break;
                case VENUE:
                    venueService.updateScore(vo);
                    break;
                default:
                    log.error("非法更新商品分数 [{}]", vo);
                    break;
            }
        });
    }

    /**
     * 民宿,零售,线路,门票,餐饮 (延迟)
     */
    @RabbitListener(queues = {QueueConstant.HOMESTAY_COMPLETE_DELAY_QUEUE, QueueConstant.LINE_COMPLETE_DELAY_QUEUE, QueueConstant.ITEM_COMPLETE_DELAY_QUEUE, QueueConstant.TICKET_COMPLETE_DELAY_QUEUE, QueueConstant.RESTAURANT_COMPLETE_DELAY_QUEUE})
    public void orderCompleteDelay(String orderNo, Message message, Channel channel) throws IOException {
        processMessageAck(orderNo, message, channel, orderEvaluationService::createDefault);
    }

    /**
     * 民宿,零售,线路,门票,餐饮 (实时)
     */
    @RabbitListener(queues = QueueConstant.ORDER_COMPLETE_QUEUE)
    public void orderComplete(String orderNo, Message message, Channel channel) throws IOException {
        processMessageAck(orderNo, message, channel, s -> log.info("订单完成同步消费消息 [{}]", orderNo));
    }

    /**
     * 民宿,零售,线路,门票,餐饮 (延迟分账)
     */
    @RabbitListener(queues = QueueConstant.ORDER_COMPLETE_ROUTING_QUEUE)
    public void orderDelayRouting(String orderNo, Message message, Channel channel) throws IOException {
        processMessageAck(orderNo, message, channel, orderService::routing);
    }

    /**
     * 零售 自动确认收货
     */
    @RabbitListener(queues = QueueConstant.ITEM_SIPPING_QUEUE)
    public void itemSipping(String orderNo, Message message, Channel channel) throws IOException {
        processMessageAck(orderNo, message, channel, s -> orderService.confirmReceipt(orderNo, null));
    }

    /**
     * 会员访问记录
     */
    @RabbitListener(queues = QueueConstant.MEMBER_VISIT_LOG_QUEUE)
    public void visitLog(MemberVisitLog visitLog, Message message, Channel channel) throws IOException {
        processMessageAck(visitLog, message, channel, memberVisitLogService::insert);
    }

    /**
     * 拼团订单过期自动取消
     * 该拼团活动下,所有没有拼团成功的订单都发起退款处理
     */
    @RabbitListener(queues = QueueConstant.GROUP_ORDER_EXPIRE_QUEUE)
    public void groupOrderExpire(GroupOrderCancelVO vo, Message message, Channel channel) throws IOException {
        processMessageAck(vo, message, channel, this::cancelGroupOrder);
    }

    /**
     * 拼团订单过期自动取消 (单个)
     */
    @RabbitListener(queues = QueueConstant.GROUP_ORDER_EXPIRE_SINGLE_QUEUE)
    public void groupOrderExpireSingle(String bookingNo, Message message, Channel channel) throws IOException {
        processMessageAck(bookingNo, message, channel, this::cancelGroupOrder);
    }

    /**
     * 零售退款自动审核通过
     */
    @RabbitListener(queues = QueueConstant.ITEM_REFUND_CONFIRM_QUEUE)
    public void refundConfirm(RefundAudit audit, Message message, Channel channel) throws IOException {
        processMessageAck(audit, message, channel, a -> {
            RefundAuditContext context = DataUtil.copy(audit, RefundAuditContext.class);
            context.setState(1);
            context.setAuditUserId(1L);
            // 备注信息标注是谁审批的 方便快速查看
            context.setAuditRemark("系统: 自动审核通过");
            redisLock.lockVoid(LockKey.ORDER_LOCK + audit.getOrderNo(), 10_000, () ->
                    stateHandler.fireEvent(ProductType.ITEM, OrderState.REFUND.getValue(), ItemEvent.REFUND_PASS, context)
            );
        });
    }

    /**
     * 支付成功订单支付金额统计
     */
    @RabbitListener(queues = QueueConstant.ORDER_PAY_RANKING_QUEUE)
    public void orderPayRanking(OrderPayNotify notify, Message message, Channel channel) throws IOException {
        processMessageAck(notify, message, channel, s ->
                orderService.incrementAmount(notify.getProductType(), notify.getMerchantId(), notify.getProductId(), notify.getAmount()));
    }

    /**
     * 取消拼团订单 (单个拼团)
     *
     * @param bookingNo 拼团订单号
     */
    private void cancelGroupOrder(String bookingNo) {
        log.info("开始取消拼团订单(个人) [{}]", bookingNo);
        List<ItemGroupOrder> groupList = itemGroupOrderService.getGroupList(bookingNo, 0);
        if (groupList.isEmpty()) {
            log.warn("该拼团订单可能已成团或已取消,不做取消处理 [{}]", bookingNo);
            return;
        }
        this.doCancelGroupOrder(groupList);
    }

    /**
     * 取消拼团订单 (全部)
     *
     * @param vo 拼团信息
     */
    private void cancelGroupOrder(GroupOrderCancelVO vo) {
        log.info("开始取消拼团订单(全部) [{}]", vo.getBookingId());
        GroupBooking booking = groupBookingService.getById(vo.getBookingId());
        if (booking == null) {
            log.warn("该拼团订单可能已删除 [{}]", vo.getBookingId());
            return;
        }
        if (booking.getEndTime().isAfter(vo.getEndTime())) {
            log.warn("拼团活动推后啦 [{}] [{}] [{}]", vo.getBookingId(), booking.getEndTime(), vo.getEndTime());
            return;
        }
        if (booking.getEndTime().isBefore(vo.getEndTime())) {
            log.warn("拼团活动提前啦 [{}] [{}] [{}]", vo.getBookingId(), booking.getEndTime(), vo.getEndTime());
            return;
        }
        List<ItemGroupOrder> groupList = itemGroupOrderService.getGroupList(vo.getBookingId(), 0);
        this.doCancelGroupOrder(groupList);
    }

    /**
     * 拼团订单取消
     *
     * @param groupList 拼团订单
     */
    private void doCancelGroupOrder(List<ItemGroupOrder> groupList) {
        for (ItemGroupOrder order : groupList) {
            redisLock.lockVoid(LockKey.ORDER_LOCK + order.getOrderNo(), 10_000, () -> orderProxyService.doCancelGroupOrder(order));
        }
    }

    /**
     * 订单30分钟过期处理
     *
     * @param orderNo 订单编号
     * @param event   状态机事件, 不同品类事件不一样
     * @param message mq消息
     * @param channel mq channel
     */
    private void doOrderExpire(String orderNo, IEvent event, Message message, Channel channel) throws IOException {
        log.info("订单自动过期处理 [{}]", orderNo);
        OrderCancelContext context = new OrderCancelContext();
        context.setOrderNo(orderNo);
        processMessageAck(context, message, channel, orderCancelContext -> {
            Order order = orderService.getByOrderNo(orderNo);
            if (order == null) {
                log.warn("订单已删除, 无须自动取消 [{}]", orderNo);
                return;
            }
            stateHandler.fireEvent(ProductType.prefix(orderNo), order.getState().getValue(), event, context);
        });
    }

    /**
     * 处理MQ中消息,并手动确认,并将结果放入缓存方便客户端查询
     *
     * @param msg      消息
     * @param message  message
     * @param channel  channel
     * @param consumer 业务
     * @param <T>      消息类型
     * @throws IOException e
     */
    public <T extends AsyncKey> void processMessageAckAsync(T msg, Message message, Channel channel, Consumer<T> consumer) throws IOException {
        try {
            log.info("开始处理MQ异步消息 [{}]", jsonService.toJson(msg));
            if (this.canConsumer(msg.getKey())) {
                consumer.accept(msg);
                // 消费成功,将结果放入缓存方便前端查询结果
                cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), SUCCESS_PLACE_HOLDER, CommonConstant.ASYNC_MSG_EXPIRE);
            } else {
                log.warn("消息已超时,不做任何业务处理 [{}]", msg);
            }
        } catch (BusinessException e) {
            log.error("队列[{}]处理消息业务异常 [{}] [{}] [{}] [{}]", message.getMessageProperties().getConsumerQueue(), msg, message, e.getCode(), e.getMessage());
            cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), e.getMessage(), CommonConstant.ASYNC_MSG_EXPIRE);
        } catch (Exception e) {
            log.error("队列[{}]处理消息异常 [{}] [{}]", message.getMessageProperties().getConsumerQueue(), msg, message, e);
            cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), ERROR_PLACE_HOLDER, CommonConstant.ASYNC_MSG_EXPIRE);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    /**
     * 判断消费是否可以要继续处理下单逻辑
     * 1. 消息在队列长达30分钟, 肯定不允许下单成功
     * 2. 如果前端一直轮训获取结果,到上限后会直接提示商品太火爆,因此如果下单还在队列中,则不允许下单
     *
     * @param asyncKey key
     * @return 是否允许下单
     */
    private boolean canConsumer(String asyncKey) {
        String hasValue = cacheService.getValue(CacheConstant.MQ_ASYNC_KEY + asyncKey);
        // 可能key过期了
        if (StrUtil.isBlank(hasValue)) {
            return false;
        }
        String accessStr = hasValue.replace(CacheConstant.PLACE_HOLDER, "");
        // 前端还没请求呢, 可以直接处理
        if (StrUtil.isBlank(accessStr)) {
            return true;
        }
        // 表示已经处理过了, 此次是重试
        if (accessStr.contains(ERROR_PLACE_HOLDER)) {
            return true;
        }
        int accessNum = Integer.parseInt(accessStr);
        return accessNum < CommonConstant.MAX_ACCESS_NUM;
    }

}
