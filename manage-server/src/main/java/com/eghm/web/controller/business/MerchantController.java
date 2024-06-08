package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.annotation.SkipPerm;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.merchant.MerchantAddRequest;
import com.eghm.dto.business.merchant.MerchantEditRequest;
import com.eghm.dto.business.merchant.MerchantQueryRequest;
import com.eghm.dto.business.merchant.MerchantRateRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.Merchant;
import com.eghm.service.business.MerchantService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.merchant.BaseMerchantResponse;
import com.eghm.vo.business.merchant.MerchantResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/24 15:13
 */
@RestController
@Api(tags = "商户管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/merchant", produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<MerchantResponse>> listPage(@Validated MerchantQueryRequest request) {
        Page<MerchantResponse> merchantPage = merchantService.getByPage(request);
        return RespBody.success(PageData.toPage(merchantPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@RequestBody @Validated MerchantAddRequest request) {
        merchantService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@RequestBody @Validated MerchantEditRequest request) {
        merchantService.update(request);
        return RespBody.success();
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public RespBody<Merchant> detail(@Validated IdDTO dto) {
        Merchant merchant = merchantService.selectByIdRequired(dto.getId());
        return RespBody.success(merchant);
    }

    @PostMapping(value = "/lock", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("账号锁定")
    public RespBody<Void> lock(@RequestBody @Validated IdDTO dto) {
        merchantService.lock(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/unlock", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("账号解锁")
    public RespBody<Void> unlock(@RequestBody @Validated IdDTO dto) {
        merchantService.unlock(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/unbind", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("解绑")
    public RespBody<Void> unbind(@RequestBody @Validated IdDTO dto) {
        merchantService.unbind(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/resetPwd", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("重置密码")
    public RespBody<Void> resetPwd(@RequestBody @Validated IdDTO dto) {
        merchantService.resetPwd(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/adjustRate", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("调整费率")
    public RespBody<Void> adjustRate(@RequestBody @Validated MerchantRateRequest request) {
        merchantService.adjustRate(request.getId(), request.getPlatformServiceRate());
        return RespBody.success();
    }

    @PostMapping(value = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("注销")
    public RespBody<Void> logout(@RequestBody @Validated IdDTO dto) {
        merchantService.logout(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, MerchantQueryRequest request) {
        List<MerchantResponse> byPage = merchantService.getList(request);
        EasyExcelUtil.export(response, "商户信息", byPage, MerchantResponse.class);
    }
    
    @GetMapping("/list")
    @ApiOperation("列表")
    @SkipPerm
    public RespBody<List<BaseMerchantResponse>> list() {
        List<BaseMerchantResponse> merchantList = merchantService.getList();
        return RespBody.success(merchantList);
    }
}
