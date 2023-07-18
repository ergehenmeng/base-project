package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.AppVersion;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.version.VersionAddRequest;
import com.eghm.dto.version.VersionEditRequest;
import com.eghm.dto.version.VersionQueryRequest;
import com.eghm.service.common.AppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2019/8/22 15:08
 */
@RestController
@Api(tags = "版本管理")
@AllArgsConstructor
@RequestMapping("/manage/version")
public class AppVersionController {

    private final AppVersionService appVersionService;

    @GetMapping("/listPage")
    @ApiOperation("查询版本列表")
    public RespBody<PageData<AppVersion>> listPage(VersionQueryRequest request) {
        Page<AppVersion> byPage = appVersionService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping("/create")
    @ApiOperation("新增版本信息")
    public RespBody<Void> create(@Validated @RequestBody VersionAddRequest request) {
        appVersionService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("编辑版本信息")
    public RespBody<Void> update(@Validated @RequestBody VersionEditRequest request) {
        appVersionService.update(request);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除版本信息")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        appVersionService.delete(dto.getId());
        return RespBody.success();
    }
}
