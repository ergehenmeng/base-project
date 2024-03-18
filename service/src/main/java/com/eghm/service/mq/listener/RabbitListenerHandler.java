package com.eghm.service.mq.listener;

import cn.hutool.core.util.StrUtil;
import com.eghm.constant.CacheConstant;
import com.eghm.constant.CommonConstant;
import com.eghm.constant.QueueConstant;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.dto.ext.LoginRecord;
import com.eghm.dto.ext.RefundAudit;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.*;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.model.ManageLog;
import com.eghm.model.MemberVisitLog;
import com.eghm.model.Order;
import com.eghm.model.WebappLog;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.context.*;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.JsonService;
import com.eghm.service.member.LoginService;
import com.eghm.service.sys.DingTalkService;
import com.eghm.service.sys.ManageLogService;
import com.eghm.service.sys.WebappLogService;
import com.eghm.state.machine.StateHandler;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.group.GroupOrderCancelVO;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

import static com.eghm.constant.CacheConstant.ERROR_PLACE_HOLDER;
import static com.eghm.constant.CacheConstant.SUCCESS_PLACE_HOLDER;

/**
 * @author 二哥很猛
 * @since 2022/7/28
 */
@Component
@AllArgsConstructor
@Slf4j
public class RabbitListenerHandler {

    private final WebappLogService webappLogService;

    private final LoginService loginService;

    private final ManageLogService manageLogService;

    private final CacheService cacheService;

    private final JsonService jsonService;

    private final StateHandler stateHandler;

    private final OrderService orderService;

    private final ItemService itemService;

    private final ScenicTicketService scenicTicketService;

    private final LineService lineService;

    private final HomestayService homestayService;

    private final RestaurantService restaurantService;

    private final OrderEvaluationService orderEvaluationService;

    private final MemberVisitLogService memberVisitLogService;

    private final OrderProxyService orderProxyService;

    private final VenueService venueService;

    private final MemberCouponService memberCouponService;

    private final DingTalkService dingTalkService;

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
     * 管理后台操作日志
     */
    @RabbitListener(queues = QueueConstant.MANAGE_OP_LOG_QUEUE)
    public void manageOpLog(ManageLog log, Message message, Channel channel) throws IOException {
        processMessageAck(log, message, channel, manageLogService::insertManageLog);
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
            stateHandler.fireEvent(ProductType.RESTAURANT, OrderState.NONE.getValue(), VoucherEvent.CREATE_QUEUE, context);
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
                case RESTAURANT:
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
        processMessageAck(vo, message, channel, orderProxyService::cancelGroupOrder);
    }

    /**
     * 拼团订单过期自动取消 (单个)
     */
    @RabbitListener(queues = QueueConstant.GROUP_ORDER_EXPIRE_SINGLE_QUEUE)
    public void groupOrderExpireSingle(String bookingNo, Message message, Channel channel) throws IOException {
        processMessageAck(bookingNo, message, channel, orderProxyService::cancelGroupOrder);
    }

    /**
     * 优惠券自动过期
     */
    @RabbitListener(queues = QueueConstant.COUPON_EXPIRE_QUEUE)
    public void couponExpire(Long couponId, Message message, Channel channel) throws IOException {
        processMessageAck(couponId, message, channel, memberCouponService::couponExpire);
    }

    /**
     * 零售退款自动审核通过
     */
    @RabbitListener(queues = QueueConstant.ITEM_REFUND_CONFIRM_QUEUE)
    public void couponExpire(RefundAudit audit, Message message, Channel channel) throws IOException {
        processMessageAck(audit, message, channel, a -> {
            RefundAuditContext context = DataUtil.copy(audit, RefundAuditContext.class);
            context.setState(1);
            context.setAuditUserId(1L);
            // 备注信息标注是谁审批的 方便快速查看
            context.setAuditRemark("系统: 自动审核通过");
            stateHandler.fireEvent(ProductType.ITEM, OrderState.REFUND.getValue(), ItemEvent.REFUND_PASS, context);
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
                log.warn("消息已超时,不做任何业务处理");
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
     * 处理MQ中消息,并手动确认
     *
     * @param msg      消息
     * @param message  message
     * @param channel  channel
     * @param consumer 业务
     * @param <T>      消息类型
     * @throws IOException e
     */
    public <T> void processMessageAck(T msg, Message message, Channel channel, Consumer<T> consumer) throws IOException {
        try {
            consumer.accept(msg);
        } catch (Exception e) {
            log.error("队列[{}]处理消息异常 [{}] [{}]", message.getMessageProperties().getConsumerQueue(), msg, message, e);
            dingTalkService.sendMsg(String.format("队列[%s]消息消费失败[%s]", message.getMessageProperties().getConsumerQueue(), jsonService.toJson(msg)));
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
