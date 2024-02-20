package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.withdraw.WithdrawApplyDTO;
import com.eghm.dto.business.withdraw.WithdrawQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.WithdrawLog;
import com.eghm.service.business.WithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */
@RestController
@Api(tags = "商户提现")
@AllArgsConstructor
@RequestMapping("/manage/merchant/withdraw")
public class WithdrawController {

    private final WithdrawService withdrawService;

    @GetMapping("/listPage")
    @ApiOperation("提现列表")
    public RespBody<PageData<WithdrawLog>> listPage(WithdrawQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<WithdrawLog> roomPage = withdrawService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @PostMapping("/apply")
    @ApiOperation("提现金额")
    public RespBody<Void> apply(@Validated @RequestBody WithdrawApplyDTO dto) {
        dto.setMerchantId(SecurityHolder.getMerchantId());
        withdrawService.apply(dto);
        return RespBody.success();
    }

}
