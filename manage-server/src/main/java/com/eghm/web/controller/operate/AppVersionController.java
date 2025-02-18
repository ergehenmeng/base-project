package com.eghm.web.controller.operate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.operate.version.VersionAddRequest;
import com.eghm.dto.operate.version.VersionEditRequest;
import com.eghm.dto.operate.version.VersionQueryRequest;
import com.eghm.dto.poi.StateRequest;
import com.eghm.service.common.AppVersionService;
import com.eghm.vo.version.AppVersionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2019/8/22 15:08
 */
@RestController
@Api(tags = "版本管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/version", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppVersionController {

    private final AppVersionService appVersionService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<AppVersionResponse>> listPage(VersionQueryRequest request) {
        Page<AppVersionResponse> byPage = appVersionService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody VersionAddRequest request) {
        appVersionService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody VersionEditRequest request) {
        appVersionService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/updateState", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("上下架")
    public RespBody<Void> updateState(@Validated @RequestBody StateRequest request) {
        appVersionService.updateState(request.getId(), request.getState());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        appVersionService.delete(dto.getId());
        return RespBody.success();
    }
}
