package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.merchant.MerchantUserAddRequest;
import com.eghm.dto.business.merchant.MerchantUserEditRequest;
import com.eghm.dto.business.merchant.MerchantUserQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.MerchantUserService;
import com.eghm.vo.business.merchant.MerchantUserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛 2023/8/24 19:34
 */
@RestController
@Api(tags = "商户用户管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/merchant/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantUserController {

    private final MerchantUserService merchantUserService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<MerchantUserResponse>> listPage(MerchantUserQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<MerchantUserResponse> merchantPage = merchantUserService.getByPage(request);
        return RespBody.success(PageData.toPage(merchantPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("创建")
    public RespBody<Void> create(@RequestBody @Validated MerchantUserAddRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        merchantUserService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@RequestBody @Validated MerchantUserEditRequest request) {
        merchantUserService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/lock", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("锁定")
    public RespBody<Void> lock(@RequestBody @Validated IdDTO dto) {
        merchantUserService.lockUser(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/unlock", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("解锁")
    public RespBody<Void> unlock(@RequestBody @Validated IdDTO dto) {
        merchantUserService.unlockUser(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        merchantUserService.deleteById(dto.getId());
        return RespBody.success();
    }

}
