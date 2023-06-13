package com.eghm.web.controller.business;

import cn.hutool.core.util.StrUtil;
import com.eghm.dto.business.order.OrderPayDTO;
import com.eghm.dto.business.order.homestay.HomestayOrderCreateDTO;
import com.eghm.dto.business.order.item.ItemOrderCreateDTO;
import com.eghm.dto.business.order.line.LineOrderCreateDTO;
import com.eghm.dto.business.order.restaurant.RestaurantOrderCreateDTO;
import com.eghm.dto.business.order.ticket.TicketOrderCreateDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.enums.ErrorCode;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.access.impl.*;
import com.eghm.service.business.handler.context.*;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.OrderVO;
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
    public OrderVO<String> itemCreate(@RequestBody @Validated ItemOrderCreateDTO dto) {
        ItemOrderCreateContext context = DataUtil.copy(dto, ItemOrderCreateContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        itemAccessHandler.createOrder(context);
        return this.generateResult(context, context.getOrderNo());
    }

    @PostMapping("/ticket/create")
    @ApiOperation("门票创建订单")
    public OrderVO<String> ticketCreate(@RequestBody @Validated TicketOrderCreateDTO dto) {
        TicketOrderCreateContext context = DataUtil.copy(dto, TicketOrderCreateContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        ticketAccessHandler.createOrder(context);
        return this.generateResult(context, context.getOrderNo());
    }

    @PostMapping("/homestay/create")
    @ApiOperation("民宿创建订单")
    public OrderVO<String> homestayCreate(@RequestBody @Validated HomestayOrderCreateDTO dto) {
        HomestayOrderCreateContext context = DataUtil.copy(dto, HomestayOrderCreateContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        homestayAccessHandler.createOrder(context);
        return this.generateResult(context, context.getOrderNo());
    }

    @PostMapping("/line/create")
    @ApiOperation("线路创建订单")
    public OrderVO<String> lineCreate(@RequestBody @Validated LineOrderCreateDTO dto) {
        LineOrderCreateContext context = DataUtil.copy(dto, LineOrderCreateContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        lineAccessHandler.createOrder(context);
        return this.generateResult(context, context.getOrderNo());
    }

    @PostMapping("/restaurant/create")
    @ApiOperation("餐饮创建订单")
    public OrderVO<String> restaurantCreate(@RequestBody @Validated RestaurantOrderCreateDTO dto) {
        RestaurantOrderCreateContext context = DataUtil.copy(dto, RestaurantOrderCreateContext.class);
        context.setMemberId(ApiHolder.getMemberId());
        restaurantAccessHandler.createOrder(context);
        return this.generateResult(context, context.getOrderNo());
    }

    @PostMapping("/pay")
    @ApiOperation("拉起支付")
    public PrepayVO pay(@RequestBody @Validated OrderPayDTO dto) {
        return orderService.createPrepay(dto.getOrderNo(), dto.getBuyerId(), dto.getTradeType());
    }

    /**
     * 生成订单结果
     * @param context 队列下单时不为空
     * @param orderNo 订单编号
     * @return vo
     */
    private OrderVO<String> generateResult(AsyncKey context, String orderNo) {
        OrderVO<String> vo = new OrderVO<>();
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
