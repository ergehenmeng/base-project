package com.eghm.web.controller.business;

import cn.hutool.core.util.StrUtil;
import com.eghm.dto.business.order.OrderPayDTO;
import com.eghm.dto.business.order.homestay.HomestayOrderCreateDTO;
import com.eghm.dto.business.order.item.ItemOrderCreateDTO;
import com.eghm.dto.business.order.line.LineOrderCreateDTO;
import com.eghm.dto.business.order.refund.*;
import com.eghm.dto.business.order.restaurant.RestaurantOrderCreateDTO;
import com.eghm.dto.business.order.ticket.TicketOrderCreateDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ErrorCode;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.access.impl.*;
import com.eghm.service.business.handler.context.*;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.OrderCreateVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyb
 * @since 2023/5/5
 */
@AccessToken
@RestController
@Api(tags = "订单")
@AllArgsConstructor
@RequestMapping("/webapp/order")
public class OrderController {

    private final ItemAccessHandler itemAccessHandler;

    private final TicketAccessHandler ticketAccessHandler;

    private final HomestayAccessHandler homestayAccessHandler;

    private final LineAccessHandler lineAccessHandler;

    private final RestaurantAccessHandler restaurantAccessHandler;

    private final OrderService orderService;

    @PostMapping("/item/create")
    @ApiOperation("零售创建订单")
    public RespBody<OrderCreateVO<String>> itemCreate(@RequestBody @Validated ItemOrderCreateDTO dto) {
        ItemOrderCreateContext context = DataUtil.copy(dto, ItemOrderCreateContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        itemAccessHandler.createOrder(context);
        OrderCreateVO<String> result = this.generateResult(context, context.getOrderNo());
        return RespBody.success(result);
    }

    @PostMapping("/ticket/create")
    @ApiOperation("门票创建订单")
    public RespBody<OrderCreateVO<String>> ticketCreate(@RequestBody @Validated TicketOrderCreateDTO dto) {
        TicketOrderCreateContext context = DataUtil.copy(dto, TicketOrderCreateContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        ticketAccessHandler.createOrder(context);
        OrderCreateVO<String> result = this.generateResult(context, context.getOrderNo());
        return RespBody.success(result);
    }

    @PostMapping("/homestay/create")
    @ApiOperation("民宿创建订单")
    public RespBody<OrderCreateVO<String>> homestayCreate(@RequestBody @Validated HomestayOrderCreateDTO dto) {
        HomestayOrderCreateContext context = DataUtil.copy(dto, HomestayOrderCreateContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        homestayAccessHandler.createOrder(context);
        OrderCreateVO<String> result = this.generateResult(context, context.getOrderNo());
        return RespBody.success(result);
    }

    @PostMapping("/line/create")
    @ApiOperation("线路创建订单")
    public RespBody<OrderCreateVO<String>> lineCreate(@RequestBody @Validated LineOrderCreateDTO dto) {
        LineOrderCreateContext context = DataUtil.copy(dto, LineOrderCreateContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        lineAccessHandler.createOrder(context);
        OrderCreateVO<String> result = this.generateResult(context, context.getOrderNo());
        return RespBody.success(result);
    }

    @PostMapping("/restaurant/create")
    @ApiOperation("餐饮创建订单")
    public RespBody<OrderCreateVO<String>> restaurantCreate(@RequestBody @Validated RestaurantOrderCreateDTO dto) {
        RestaurantOrderCreateContext context = DataUtil.copy(dto, RestaurantOrderCreateContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        restaurantAccessHandler.createOrder(context);
        OrderCreateVO<String> result = this.generateResult(context, context.getOrderNo());
        return RespBody.success(result);
    }

    @PostMapping("/pay")
    @ApiOperation("拉起支付")
    public RespBody<PrepayVO> pay(@RequestBody @Validated OrderPayDTO dto) {
        PrepayVO vo = orderService.createPrepay(dto.getOrderNo(), dto.getBuyerId(), dto.getTradeType());
        return RespBody.success(vo);
    }

    @PostMapping("ticket/refundApply")
    @ApiOperation("门票退款申请")
    public RespBody<Void> ticketRefundApply(@RequestBody @Validated TicketRefundApplyDTO dto) {
        RefundApplyContext context = DataUtil.copy(dto, RefundApplyContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        context.setApplyType(1);
        ticketAccessHandler.refundApply(context);
        return RespBody.success();
    }

    @PostMapping("line/refundApply")
    @ApiOperation("线路退款申请")
    public RespBody<Void> lineRefundApply(@RequestBody @Validated LineRefundApplyDTO dto) {
        RefundApplyContext context = DataUtil.copy(dto, RefundApplyContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        context.setApplyType(1);
        lineAccessHandler.refundApply(context);
        return RespBody.success();
    }

    @PostMapping("homestay/refundApply")
    @ApiOperation("民宿退款申请")
    public RespBody<Void> homestayRefundApply(@RequestBody @Validated HomestayRefundApplyDTO dto) {
        RefundApplyContext context = DataUtil.copy(dto, RefundApplyContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        context.setApplyType(1);
        homestayAccessHandler.refundApply(context);
        return RespBody.success();
    }

    @PostMapping("restaurant/refundApply")
    @ApiOperation("餐饮退款申请")
    public RespBody<Void> restaurantRefundApply(@RequestBody @Validated RestaurantRefundApplyDTO dto) {
        RefundApplyContext context = DataUtil.copy(dto, RefundApplyContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        context.setApplyType(1);
        restaurantAccessHandler.refundApply(context);
        return RespBody.success();
    }

    @PostMapping("item/refundApply")
    @ApiOperation("零售退款申请")
    public RespBody<Void> itemRefundApply(@RequestBody @Validated ItemRefundApplyDTO dto) {
        RefundApplyContext context = DataUtil.copy(dto, RefundApplyContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        context.setItemOrderId(dto.getOrderId());
        restaurantAccessHandler.refundApply(context);
        return RespBody.success();
    }

    /**
     * 生成订单结果
     * @param context 队列下单时不为空
     * @param orderNo 订单编号
     * @return vo
     */
    private OrderCreateVO<String> generateResult(AsyncKey context, String orderNo) {
        OrderCreateVO<String> vo = new OrderCreateVO<>();
        if (StrUtil.isNotBlank(context.getKey())) {
            vo.setState(0);
            vo.setData(context.getKey());
            return vo;
        }
        if (StrUtil.isNotBlank(orderNo)) {
            vo.setState(1);
            vo.setData(orderNo);
            return vo;
        }
        // 极端情况, mq发送失败时会发生
        vo.setState(2);
        vo.setErrorMsg(ErrorCode.ORDER_ERROR.getMsg());
        return vo;
    }

}
