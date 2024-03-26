package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.account.AccountQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.AccountLogService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.account.AccountLogResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@RestController
@Api(tags = "商户资金变动")
@AllArgsConstructor
@RequestMapping(value = "/manage/merchant/account/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountLogController {

    private final AccountLogService accountLogService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<AccountLogResponse>> listPage(AccountQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<AccountLogResponse> byPage = accountLogService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @ApiOperation("导出")
    @GetMapping("/export")
    public void export(HttpServletResponse response, AccountQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<AccountLogResponse> byPage = accountLogService.getList(request);
        EasyExcelUtil.export(response, "资金变动记录", byPage, AccountLogResponse.class);
    }
}
