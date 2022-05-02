package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.constant.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.model.AppVersion;
import com.eghm.model.dto.ext.FilePath;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author 二哥很猛
 * @date 2019/8/22 15:08
 */
@RestController
@Api(tags = "版本管理")
@AllArgsConstructor
public class AppVersionController {

    private final AppVersionService appVersionService;

    private final FileService fileService;

    private final SysConfigApi sysConfigApi;

    /**
     * app版本管理列表
     */
    @GetMapping("/version/list_page")
    @ApiOperation("查询版本列表")
    public Paging<AppVersion> listPage(VersionQueryRequest request) {
        Page<AppVersion> byPage = appVersionService.getByPage(request);
        return new Paging<>(byPage);
    }

    @GetMapping("/version/manage_page")
    public String managePage(Model model) {
        model.addAttribute("address", sysConfigApi.getString(ConfigConstant.FILE_SERVER_ADDRESS));
        return "version/manage_page";
    }

    /**
     * 添加软件版本页面
     */
    @GetMapping("/version/add_page")
    public String addPage(Model model) {
        String appStoreUrl = sysConfigApi.getString(ConfigConstant.APP_STORE_URL);
        model.addAttribute("appStoreUrl", appStoreUrl);
        return "version/add_page";
    }

    /**
     * 添加app版本信息
     */
    @PostMapping("/version/add")
    @Mark
    @ApiImplicitParam(name = "file", value = "上传的文件", paramType = "formData", dataType = "file", required = true)
    @ApiOperation("新增版本信息")
    public RespBody<Object> add(@Valid VersionAddRequest request, @RequestParam("file") MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            long maxSize = sysConfigApi.getLong(ConfigConstant.ANDROID_MAX_SIZE);
            FilePath filePath = fileService.saveFile(file, CommonConstant.VERSION, maxSize);
            request.setUrl(filePath.getPath());
        }
        appVersionService.addAppVersion(request);
        return RespBody.success();
    }

    /**
     * 编辑保存app版本信息
     */
    @PostMapping("/version/edit")
    @Mark
    @ApiOperation("编辑版本信息")
    public RespBody<Object> edit(@Valid VersionEditRequest request) {
        appVersionService.editAppVersion(request);
        return RespBody.success();
    }

    /**
     * 上架app版本
     *
     * @param id 主键
     */
    @PostMapping("/version/put_away")
    @Mark
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @ApiOperation("版本上架")
    public RespBody<Object> putAway(@RequestParam("id") Long id) {
        appVersionService.putAwayVersion(id);
        return RespBody.success();
    }

    /**
     * 下架app版本
     *
     * @param id 主键
     */
    @PostMapping("/version/sold_out")
    @Mark
    @ApiOperation("版本下架")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public RespBody<Object> soldOut(@RequestParam("id") Long id) {
        appVersionService.soldOutVersion(id);
        return RespBody.success();
    }

    /**
     * 删除版本信息
     */
    @PostMapping("/version/delete")
    @Mark
    @ApiOperation("删除版本信息")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public RespBody<Object> delete(@RequestParam("id") Long id) {
        appVersionService.deleteVersion(id);
        return RespBody.success();
    }
}
