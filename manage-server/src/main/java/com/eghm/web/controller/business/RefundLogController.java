package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.refund.RefundLogQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.vo.order.refund.RefundLogResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/listPage")
    @ApiOperation("退款申请列表")
    public PageData<RefundLogResponse> listPage(RefundLogQueryRequest request) {
        Page<RefundLogResponse> roomPage = orderRefundLogService.getByPage(request);
        return PageData.toPage(roomPage);
    }
}
