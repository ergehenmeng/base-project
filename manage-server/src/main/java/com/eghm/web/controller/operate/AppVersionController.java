package com.eghm.web.controller.operate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.StateRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.operate.version.VersionAddRequest;
import com.eghm.dto.operate.version.VersionEditRequest;
import com.eghm.dto.operate.version.VersionQueryRequest;
import com.eghm.service.operate.AppVersionService;
import com.eghm.vo.operate.version.AppVersionResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2019/8/22 15:08
 */
@RestController
@Tag(name = "App管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/version", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppVersionController {

    private final AppVersionService appVersionService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<AppVersionResponse>> listPage(@ParameterObject VersionQueryRequest request) {
        Page<AppVersionResponse> byPage = appVersionService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody VersionAddRequest request) {
        appVersionService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody VersionEditRequest request) {
        appVersionService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/updateState", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "上下架")
    public RespBody<Void> updateState(@Validated @RequestBody StateRequest request) {
        appVersionService.updateState(request.getId(), request.getState());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        appVersionService.delete(dto.getId());
        return RespBody.success();
    }
}
