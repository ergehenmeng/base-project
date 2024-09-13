package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.LockKey;
import com.eghm.dto.business.order.OrderDTO;
import com.eghm.dto.business.order.refund.RefundAuditRequest;
import com.eghm.dto.business.order.refund.RefundLogQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.lock.RedisLock;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.state.machine.access.AccessHandler;
import com.eghm.state.machine.context.RefundAuditContext;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.item.ItemOrderRefundDetailResponse;
import com.eghm.vo.business.order.refund.RefundLogResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.eghm.enums.ErrorCode.ORDER_TYPE_MATCH;

/**
 * @author wyb
 * @since 2023/6/7
 */
@RestController
@Api(tags = "退款管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/refund/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class RefundLogController {

    private final RedisLock redisLock;

    private final CommonService commonService;

    private final ItemOrderService itemOrderService;

    private final OrderRefundLogService orderRefundLogService;

    @GetMapping("/listPage")
    @ApiOperation("退款申请列表")
    public RespBody<PageData<RefundLogResponse>> listPage(RefundLogQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<RefundLogResponse> roomPage = orderRefundLogService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @GetMapping("/item/detail")
    @ApiOperation("零售退款详情")
    public RespBody<ItemOrderRefundDetailResponse> detail(@Validated OrderDTO dto) {
        ItemOrderRefundDetailResponse response = itemOrderService.refundDetail(dto.getOrderNo());
        return RespBody.success(response);
    }

    @PostMapping(value = "/audit", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("退款审核")
    public RespBody<Void> audit(@Validated @RequestBody RefundAuditRequest request) {
        ProductType productType = ProductType.prefix(request.getOrderNo());
        AccessHandler accessHandler = commonService.getHandler(productType, AccessHandler.class);
        if (accessHandler == null) {
            throw new BusinessException(ORDER_TYPE_MATCH);
        }
        RefundAuditContext context = DataUtil.copy(request, RefundAuditContext.class);
        UserToken userToken = SecurityHolder.getUserRequired();
        context.setAuditUserId(userToken.getId());
        // 备注信息标注是谁审批的 方便快速查看
        context.setAuditRemark(userToken.getNickName() + ": " + request.getAuditRemark());
        redisLock.lockVoid(LockKey.ORDER_LOCK + request.getOrderNo(), 10_000, () -> accessHandler.refundAudit(context));
        return RespBody.success();
    }
}
