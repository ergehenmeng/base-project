package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.order.OrderVerifyDTO;
import com.eghm.dto.business.verify.VerifyLogQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.access.impl.*;
import com.eghm.service.business.handler.context.OrderVerifyContext;
import com.eghm.vo.business.verify.VerifyLogResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.eghm.enums.ErrorCode.VERIFY_TYPE_ERROR;

/**
 * @author wyb
 * @since 2023/6/13
 */
@RestController
@Api(tags = "核销")
@AllArgsConstructor
@RequestMapping("/manage/verify")
public class VerifyController {

    private final VerifyLogService verifyLogService;

    private final ItemAccessHandler itemAccessHandler;

    private final TicketAccessHandler ticketAccessHandler;

    private final HomestayAccessHandler homestayAccessHandler;

    private final LineAccessHandler lineAccessHandler;

    private final RestaurantAccessHandler restaurantAccessHandler;

    private final OrderService orderService;

    @GetMapping("listPage")
    @ApiOperation("核销记录列表")
    public PageData<VerifyLogResponse> listPage(VerifyLogQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<VerifyLogResponse> roomPage = verifyLogService.getByPage(request);
        return PageData.toPage(roomPage);
    }


    @PostMapping("/verify")
    @ApiOperation("核销")
    public RespBody<Integer> verify(@RequestBody @Validated OrderVerifyDTO dto) {
        String orderNo = orderService.decryptVerifyNo(dto.getVerifyNo());
        // TODO 核销正常情况需要在管理后台登录,此处获取用户id不严谨
        OrderVerifyContext context = new OrderVerifyContext();
        context.setOrderNo(orderNo);
        context.setUserId(SecurityHolder.getUserId());
        context.setMerchantId(SecurityHolder.getMerchantId());

        context.setRemark(dto.getRemark());
        context.setVisitorList(dto.getVisitorList());

        ProductType productType = ProductType.prefix(orderNo);

        if (productType == ProductType.ITEM) {
            itemAccessHandler.verifyOrder(context);
        } else if (productType == ProductType.HOMESTAY) {
            homestayAccessHandler.verifyOrder(context);
        } else if (productType == ProductType.LINE) {
            lineAccessHandler.verifyOrder(context);
        } else if (productType == ProductType.TICKET) {
            ticketAccessHandler.verifyOrder(context);
        } else if (productType == ProductType.RESTAURANT) {
            restaurantAccessHandler.verifyOrder(context);
        } else {
            throw new BusinessException(VERIFY_TYPE_ERROR);
        }
        return RespBody.success(context.getVerifyNum());
    }
}
