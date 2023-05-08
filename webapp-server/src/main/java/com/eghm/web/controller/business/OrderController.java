package com.eghm.web.controller.business;

import com.eghm.dto.business.order.OrderPayDTO;
import com.eghm.dto.business.order.homestay.HomestayOrderCreateDTO;
import com.eghm.dto.business.order.item.ItemOrderCreateDTO;
import com.eghm.dto.business.order.line.LineOrderCreateDTO;
import com.eghm.dto.business.order.ticket.TicketOrderCreateDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.access.impl.HomestayAccessHandler;
import com.eghm.service.business.handler.access.impl.ItemAccessHandler;
import com.eghm.service.business.handler.access.impl.LineAccessHandler;
import com.eghm.service.business.handler.access.impl.TicketAccessHandler;
import com.eghm.service.business.handler.context.HomestayOrderCreateContext;
import com.eghm.service.business.handler.context.ItemOrderCreateContext;
import com.eghm.service.business.handler.context.LineOrderCreateContext;
import com.eghm.service.business.handler.context.TicketOrderCreateContext;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.utils.DataUtil;
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

    private final OrderService orderService;

    @PostMapping("/item/create")
    @ApiOperation("零售创建订单")
    public String itemCreate(@RequestBody @Validated ItemOrderCreateDTO dto) {
        ItemOrderCreateContext context = DataUtil.copy(dto, ItemOrderCreateContext.class);
        context.setUserId(ApiHolder.getUserId());
        itemAccessHandler.createOrder(context);
        return context.getOrderNo();
    }

    @PostMapping("/ticket/create")
    @ApiOperation("门票创建订单")
    public String ticketCreate(@RequestBody @Validated TicketOrderCreateDTO dto) {
        TicketOrderCreateContext context = DataUtil.copy(dto, TicketOrderCreateContext.class);
        context.setUserId(ApiHolder.getUserId());
        ticketAccessHandler.createOrder(context);
        return context.getOrderNo();
    }

    @PostMapping("/homestay/create")
    @ApiOperation("民宿创建订单")
    public String homestayCreate(@RequestBody @Validated HomestayOrderCreateDTO dto) {
        HomestayOrderCreateContext context = DataUtil.copy(dto, HomestayOrderCreateContext.class);
        context.setUserId(ApiHolder.getUserId());
        homestayAccessHandler.createOrder(context);
        return context.getOrderNo();
    }

    @PostMapping("/line/create")
    @ApiOperation("线路创建订单")
    public String lineCreate(@RequestBody @Validated LineOrderCreateDTO dto) {
        LineOrderCreateContext context = DataUtil.copy(dto, LineOrderCreateContext.class);
        context.setUserId(ApiHolder.getUserId());
        lineAccessHandler.createOrder(context);
        return context.getOrderNo();
    }

    @PostMapping("/pay")
    @ApiOperation("拉起支付")
    public PrepayVO pay(@RequestBody @Validated OrderPayDTO dto) {
        return orderService.createPrepay(dto.getOrderNo(), dto.getBuyerId(), dto.getTradeType());
    }


}
