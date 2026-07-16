package com.eghm.app.manage.controller.operate;

import com.eghm.foundation.core.utils.ResourceUtil;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.dto.IdDTO;
import com.eghm.foundation.core.dto.ext.PageData;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.platform.iam.dto.AuthConfigAddRequest;
import com.eghm.platform.iam.dto.AuthConfigEditRequest;
import com.eghm.platform.iam.service.AuthConfigService;
import com.eghm.platform.iam.vo.AuthConfigResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@RestController
@AllArgsConstructor
@Tag(name = "第三方授权")
@RequestMapping(value = "/manage/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthConfigController {

    private final AuthConfigService authConfigService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<AuthConfigResponse>> listPage(@ParameterObject PagingQuery request) {
        Page<AuthConfigResponse> byPage = authConfigService.getByPage(request);
        return RespBody.success(PageData.convert(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody AuthConfigAddRequest request) {
        authConfigService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody AuthConfigEditRequest request) {
        authConfigService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        authConfigService.deleteById(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/reset", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "重置秘钥")
    public RespBody<Void> reset(@Validated @RequestBody IdDTO dto) {
        authConfigService.reset(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/sendEmail", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "发送邮件")
    public RespBody<Void> sendEmail(@Validated @RequestBody IdDTO dto) throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:对接文档.md");
        authConfigService.sendEmail(dto.getId(), file);
        return RespBody.success();
    }
}
