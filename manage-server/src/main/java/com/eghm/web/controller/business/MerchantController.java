package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.merchant.MerchantAddRequest;
import com.eghm.dto.business.merchant.MerchantEditRequest;
import com.eghm.dto.business.merchant.MerchantQueryRequest;
import com.eghm.dto.business.merchant.MerchantRateRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.Merchant;
import com.eghm.service.business.MerchantService;
import com.eghm.utils.ExcelUtil;
import com.eghm.vo.business.merchant.MerchantResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/24 15:13
 */
@RestController
@Api(tags = "商户管理")
@AllArgsConstructor
@RequestMapping("/manage/merchant")
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/listPage")
    @ApiOperation("商户列表")
    public RespBody<PageData<MerchantResponse>> listPage(@Validated MerchantQueryRequest request) {
        Page<MerchantResponse> merchantPage = merchantService.getByPage(request);
        return RespBody.success(PageData.toPage(merchantPage));
    }

    @PostMapping("/create")
    @ApiOperation("创建商户")
    public RespBody<Void> create(@RequestBody @Validated MerchantAddRequest request) {
        merchantService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新商户")
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

    @PostMapping("/lock")
    @ApiOperation("账号锁定")
    public RespBody<Void> lock(@RequestBody @Validated IdDTO dto) {
        merchantService.lock(dto.getId());
        return RespBody.success();
    }

    @PostMapping("/unlock")
    @ApiOperation("账号解锁")
    public RespBody<Void> unlock(@RequestBody @Validated IdDTO dto) {
        merchantService.unlock(dto.getId());
        return RespBody.success();
    }

    @PostMapping("/unbind")
    @ApiOperation("解绑")
    public RespBody<Void> unbind(@RequestBody @Validated IdDTO dto) {
        merchantService.unbind(dto.getId());
        return RespBody.success();
    }

    @PostMapping("/resetPwd")
    @ApiOperation("重置密码")
    public RespBody<Void> resetPwd(@RequestBody @Validated IdDTO dto) {
        merchantService.resetPwd(dto.getId());
        return RespBody.success();
    }

    @PostMapping("/adjustRate")
    @ApiOperation("调整费率")
    public RespBody<Void> adjustRate(@RequestBody @Validated MerchantRateRequest request) {
        merchantService.adjustRate(request.getId(), request.getPlatformServiceRate());
        return RespBody.success();
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, MerchantQueryRequest request) {
        List<MerchantResponse> byPage = merchantService.getList(request);
        ExcelUtil.export(response, "商户信息", byPage, MerchantResponse.class);
    }

}
