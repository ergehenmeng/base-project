package com.eghm.controller.business;

import com.eghm.annotation.Mark;
import com.eghm.common.constant.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.model.business.Version;
import com.eghm.model.dto.business.version.VersionAddRequest;
import com.eghm.model.dto.business.version.VersionEditRequest;
import com.eghm.model.dto.business.version.VersionQueryRequest;
import com.eghm.model.ext.FilePath;
import com.eghm.model.ext.Paging;
import com.eghm.model.ext.RespBody;
import com.eghm.service.common.FileService;
import com.eghm.service.common.VersionService;
import com.eghm.service.system.impl.SystemConfigApi;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 二哥很猛
 * @date 2019/8/22 15:08
 */
@Controller
public class VersionController {

    private VersionService versionService;

    private FileService fileService;

    private SystemConfigApi systemConfigApi;

    @Autowired
    public void setVersionService(VersionService versionService) {
        this.versionService = versionService;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setSystemConfigApi(SystemConfigApi systemConfigApi) {
        this.systemConfigApi = systemConfigApi;
    }

    /**
     * app版本管理列表
     */
    @PostMapping("/business/version/list_page")
    @ResponseBody
    public Paging<Version> listPage(VersionQueryRequest request) {
        PageInfo<Version> byPage = versionService.getByPage(request);
        return new Paging<>(byPage);
    }

    @GetMapping("/business/version/manage_page")
    public String managePage(Model model) {
        model.addAttribute("address", systemConfigApi.getString(ConfigConstant.FILE_SERVER_ADDRESS));
        return "business/version/manage_page";
    }

    /**
     * 添加软件版本页面
     */
    @GetMapping("/business/version/add_page")
    public String addPage(Model model) {
        String appStoreUrl = systemConfigApi.getString(ConfigConstant.APP_STORE_URL);
        model.addAttribute("appStoreUrl", appStoreUrl);
        return "business/version/add_page";
    }

    /**
     * 添加app版本信息
     */
    @PostMapping("/business/version/add")
    @ResponseBody
    @Mark
    public RespBody<Object> add(VersionAddRequest request, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            long maxSize = systemConfigApi.getLong(ConfigConstant.ANDROID_MAX_SIZE);
            FilePath filePath = fileService.saveFile(file, CommonConstant.VERSION, maxSize);
            request.setUrl(filePath.getPath());
        }
        versionService.addAppVersion(request);
        return RespBody.success();
    }

    /**
     * 编辑保存app版本信息
     */
    @PostMapping("/business/version/edit")
    @ResponseBody
    @Mark
    public RespBody<Object> edit(VersionEditRequest request) {
        versionService.editAppVersion(request);
        return RespBody.success();
    }

    /**
     * 上架app版本
     *
     * @param id 主键
     */
    @PostMapping("/business/version/put_away")
    @ResponseBody
    @Mark
    public RespBody<Object> putAway(Integer id) {
        versionService.putAwayVersion(id);
        return RespBody.success();
    }

    /**
     * 下架app版本
     *
     * @param id 主键
     */
    @PostMapping("/business/version/sold_out")
    @ResponseBody
    @Mark
    public RespBody<Object> soldOut(Integer id) {
        versionService.soldOutVersion(id);
        return RespBody.success();
    }

    /**
     * 删除版本信息
     */
    @PostMapping("/business/version/delete")
    @ResponseBody
    @Mark
    public RespBody<Object> delete(Integer id) {
        versionService.deleteVersion(id);
        return RespBody.success();
    }
}
