package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.withdraw.WithdrawApplyDTO;
import com.eghm.dto.business.withdraw.WithdrawQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.WithdrawService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.withdraw.WithdrawLogResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */
@RestController
@Tag(name="商户提现")
@AllArgsConstructor
@RequestMapping(value = "/manage/merchant/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
public class WithdrawController {

    private final WithdrawService withdrawService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<WithdrawLogResponse>> listPage(WithdrawQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<WithdrawLogResponse> roomPage = withdrawService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @GetMapping("/export")
    @Operation(summary = "导出")
    public void export(HttpServletResponse response, WithdrawQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<WithdrawLogResponse> byPage = withdrawService.getList(request);
        EasyExcelUtil.export(response, "提现记录", byPage, WithdrawLogResponse.class);
    }

    @PostMapping(value = "/apply", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "提现申请")
    public RespBody<Void> apply(@Validated @RequestBody WithdrawApplyDTO dto) {
        dto.setMerchantId(SecurityHolder.getMerchantId());
        withdrawService.apply(dto);
        return RespBody.success();
    }

}
