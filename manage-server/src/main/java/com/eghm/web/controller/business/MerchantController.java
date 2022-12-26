package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.Merchant;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.business.merchant.MerchantAddRequest;
import com.eghm.model.dto.business.merchant.MerchantEditRequest;
import com.eghm.model.dto.business.merchant.MerchantQueryRequest;
import com.eghm.service.business.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public PageData<Merchant> listPage(@Validated MerchantQueryRequest request) {
        Page<Merchant> merchantPage = merchantService.getByPage(request);
        return PageData.toPage(merchantPage);
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

    @PostMapping("/resetPwd")
    @ApiOperation("重置密码")
    public RespBody<Void> resetPwd(@RequestBody @Validated IdDTO dto) {
        merchantService.resetPwd(dto.getId());
        return RespBody.success();
    }

}
