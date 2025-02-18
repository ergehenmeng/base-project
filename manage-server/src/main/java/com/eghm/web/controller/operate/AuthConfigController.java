package com.eghm.web.controller.operate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.operate.auth.AuthConfigAddRequest;
import com.eghm.dto.operate.auth.AuthConfigEditRequest;
import com.eghm.dto.operate.auth.AuthConfigQueryRequest;
import com.eghm.service.common.AuthConfigService;
import com.eghm.vo.auth.AuthConfigResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@RestController
@Api(tags = "第三方授权")
@AllArgsConstructor
@RequestMapping(value = "/manage/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthConfigController {

    private final AuthConfigService authConfigService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<AuthConfigResponse>> listPage(AuthConfigQueryRequest request) {
        Page<AuthConfigResponse> byPage = authConfigService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody AuthConfigAddRequest request) {
        authConfigService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody AuthConfigEditRequest request) {
        authConfigService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        authConfigService.deleteById(dto.getId());
        return RespBody.success();
    }
}
