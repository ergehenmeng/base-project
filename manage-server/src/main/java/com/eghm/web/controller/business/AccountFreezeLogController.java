package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.freeze.AccountFreezeQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.AccountFreezeLogService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.freeze.AccountFreezeLogResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/26
 */

@RestController
@Tag(name="商户冻结")
@AllArgsConstructor
@RequestMapping(value = "/manage/merchant/account/freeze", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountFreezeLogController {

    private final AccountFreezeLogService accountFreezeLogService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<AccountFreezeLogResponse>> listPage(AccountFreezeQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<AccountFreezeLogResponse> byPage = accountFreezeLogService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/export")
    @Operation(summary = "导出")
    public void export(HttpServletResponse response, AccountFreezeQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<AccountFreezeLogResponse> byPage = accountFreezeLogService.getList(request);
        EasyExcelUtil.export(response, "冻结记录", byPage, AccountFreezeLogResponse.class);
    }
}
