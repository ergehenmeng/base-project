package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.verify.VerifyLogQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.service.business.VerifyLogService;
import com.eghm.vo.business.verify.VerifyLogResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyb
 * @since 2023/6/13
 */
@RestController
@Api(tags = "核销记录")
@AllArgsConstructor
@RequestMapping("/manage/verify/log")
public class VerifyLogController {

    private final VerifyLogService verifyLogService;

    @GetMapping("/listPage")
    @ApiOperation("核销记录列表")
    public PageData<VerifyLogResponse> listPage(VerifyLogQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<VerifyLogResponse> roomPage = verifyLogService.getByPage(request);
        return PageData.toPage(roomPage);
    }
}
