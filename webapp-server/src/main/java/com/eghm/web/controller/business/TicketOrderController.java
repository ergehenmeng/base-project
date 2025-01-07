package com.eghm.web.controller.business;

import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.ticket.TicketOrderQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.VisitType;
import com.eghm.service.business.TicketOrderService;
import com.eghm.vo.business.order.ticket.TicketOrderDetailVO;
import com.eghm.vo.business.order.ticket.TicketOrderVO;
import com.eghm.web.annotation.AccessToken;
import com.eghm.web.annotation.VisitRecord;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@AccessToken
@RestController
@Tag(name="门票订单")
@AllArgsConstructor
@RequestMapping(value = "/webapp/ticket/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketOrderController {

    private final TicketOrderService ticketOrderService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<List<TicketOrderVO>> listPage(@Validated TicketOrderQueryDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<TicketOrderVO> voList = ticketOrderService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    @VisitRecord(VisitType.ORDER_DETAIL)
    public RespBody<TicketOrderDetailVO> detail(@Validated OrderDTO dto) {
        TicketOrderDetailVO detail = ticketOrderService.getDetail(dto.getOrderNo(), ApiHolder.getMemberId());
        return RespBody.success(detail);
    }
}
