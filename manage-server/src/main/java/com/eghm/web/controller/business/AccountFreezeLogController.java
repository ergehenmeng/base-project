package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.freeze.AccountFreezeQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.AccountFreezeLogService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.freeze.AccountFreezeLogResponse;
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
 * @since 2024/2/26
 */

@RestController
@Api(tags = "商户冻结")
@AllArgsConstructor
@RequestMapping(value = "/manage/merchant/account/freeze", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountFreezeLogController {

    private final AccountFreezeLogService accountFreezeLogService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<AccountFreezeLogResponse>> listPage(AccountFreezeQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<AccountFreezeLogResponse> byPage = accountFreezeLogService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, AccountFreezeQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<AccountFreezeLogResponse> byPage = accountFreezeLogService.getList(request);
        EasyExcelUtil.export(response, "冻结记录", byPage, AccountFreezeLogResponse.class);
    }
}
