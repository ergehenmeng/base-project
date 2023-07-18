package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.refund.RefundAuditRequest;
import com.eghm.dto.business.order.refund.RefundLogQueryRequest;
import com.eghm.dto.ext.UserToken;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.handler.access.impl.*;
import com.eghm.service.business.handler.context.RefundAuditContext;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.refund.RefundLogResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.eghm.enums.ErrorCode.ORDER_TYPE_MATCH;

/**
 * @author wyb
 * @since 2023/6/7
 */
@RestController
@Api(tags = "售后管理")
@AllArgsConstructor
@RequestMapping("/manage/refund/log")
public class RefundLogController {

    private final OrderRefundLogService orderRefundLogService;

    private final ItemAccessHandler itemAccessHandler;

    private final TicketAccessHandler ticketAccessHandler;

    private final HomestayAccessHandler homestayAccessHandler;

    private final LineAccessHandler lineAccessHandler;

    private final RestaurantAccessHandler restaurantAccessHandler;

    @GetMapping("/listPage")
    @ApiOperation("退款申请列表")
    public RespBody<PageData<RefundLogResponse>> listPage(RefundLogQueryRequest request) {
        Page<RefundLogResponse> roomPage = orderRefundLogService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @PostMapping("/audit")
    @ApiOperation("退款审核")
    public RespBody<Void> audit(@Validated @RequestBody RefundAuditRequest request) {
        ProductType productType = ProductType.prefix(request.getOrderNo());
        RefundAuditContext context = DataUtil.copy(request, RefundAuditContext.class);
        UserToken userToken = SecurityHolder.getUserRequired();
        context.setAuditUserId(userToken.getId());
        // 备注信息标注是谁审批的 方便快速查看
        context.setAuditRemark(userToken.getNickName() + ":" + request.getAuditRemark());

        if (productType == ProductType.ITEM) {
            itemAccessHandler.refundAudit(context);
        } else if (productType == ProductType.HOMESTAY) {
            homestayAccessHandler.refundAudit(context);
        } else if (productType == ProductType.LINE) {
            lineAccessHandler.refundAudit(context);
        } else if (productType == ProductType.TICKET) {
            ticketAccessHandler.refundAudit(context);
        } else if (productType == ProductType.RESTAURANT) {
            restaurantAccessHandler.refundAudit(context);
        } else {
            throw new BusinessException(ORDER_TYPE_MATCH);
        }
        return RespBody.success();
    }
}
