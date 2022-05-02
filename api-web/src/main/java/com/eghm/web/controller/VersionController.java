package com.eghm.web.controller;

import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.version.AppVersionVO;
import com.eghm.service.common.AppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
@RestController
@Api(tags = "更新版本")
@AllArgsConstructor
public class VersionController {

    private final AppVersionService appVersionService;

    /**
     * 获取可以下载的最新版本
     */
    @GetMapping("/version/latest_version")
    @ApiOperation("获取最新的版本")
    public RespBody<AppVersionVO> getLatestVersion() {
        AppVersionVO latestVersion = appVersionService.getLatestVersion();
        return RespBody.success(latestVersion);
    }

}
