package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.AppVersion;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.version.VersionAddRequest;
import com.eghm.model.dto.version.VersionEditRequest;
import com.eghm.model.dto.version.VersionQueryRequest;
import com.eghm.service.common.AppVersionService;
import com.eghm.service.common.FileService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.web.annotation.Mark;
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
@RequestMapping("/version")
public class AppVersionController {

    private final AppVersionService appVersionService;

    private final FileService fileService;

    private final SysConfigApi sysConfigApi;

    /**
     * app版本管理列表
     */
    @GetMapping("/listPage")
    @ApiOperation("查询版本列表")
    public Paging<AppVersion> listPage(VersionQueryRequest request) {
        Page<AppVersion> byPage = appVersionService.getByPage(request);
        return new Paging<>(byPage);
    }


    /**
     * 添加app版本信息
     */
    @PostMapping("/add")
    @Mark
    @ApiOperation("新增版本信息")
    public RespBody<Void> add(@Validated VersionAddRequest request) {
        appVersionService.addAppVersion(request);
        return RespBody.success();
    }

    /**
     * 编辑保存app版本信息
     */
    @PostMapping("/edit")
    @Mark
    @ApiOperation("编辑版本信息")
    public RespBody<Void> edit(@Validated VersionEditRequest request) {
        appVersionService.editAppVersion(request);
        return RespBody.success();
    }

    /**
     * 上架app版本
     *
     * @param id 主键
     */
    @PostMapping("/onSale")
    @Mark
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @ApiOperation("版本上架")
    public RespBody<Void> onSale(@RequestParam("id") Long id) {
        appVersionService.putAwayVersion(id);
        return RespBody.success();
    }

    /**
     * 下架app版本
     *
     * @param id 主键
     */
    @PostMapping("/soldOut")
    @Mark
    @ApiOperation("版本下架")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public RespBody<Void> soldOut(@RequestParam("id") Long id) {
        appVersionService.soldOutVersion(id);
        return RespBody.success();
    }

    /**
     * 删除版本信息
     */
    @PostMapping("/delete")
    @Mark
    @ApiOperation("删除版本信息")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public RespBody<Void> delete(@RequestParam("id") Long id) {
        appVersionService.deleteVersion(id);
        return RespBody.success();
    }
}
