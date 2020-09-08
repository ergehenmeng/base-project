package com.eghm.controller;

import com.eghm.annotation.SkipLogger;
import com.eghm.model.ext.RespBody;
import com.eghm.model.vo.version.AppVersionVO;
import com.eghm.service.common.AppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
@RestController
@Api("更新版本")
public class AppVersionController {

    private AppVersionService appVersionService;

    @Autowired
    @SkipLogger
    public void setAppVersionService(AppVersionService appVersionService) {
        this.appVersionService = appVersionService;
    }

    /**
     * 获取可以下载的最新版本
     */
    @PostMapping("/version/latest_version")
    @ApiOperation("获取最新的版本")
    public RespBody<AppVersionVO> getLatestVersion() {
        AppVersionVO latestVersion = appVersionService.getLatestVersion();
        return RespBody.success(latestVersion);
    }

}
