package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.service.common.AppVersionService;
import com.eghm.vo.version.AppVersionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 殿小二
 * @since 2020/9/4
 */
@RestController
@Api(tags = "更新版本")
@AllArgsConstructor
@RequestMapping(value = "/webapp/version", produces = MediaType.APPLICATION_JSON_VALUE)
public class VersionController {

    private final AppVersionService appVersionService;

    @GetMapping("/latest")
    @ApiOperation("获取最新的版本")
    public RespBody<AppVersionVO> latest() {
        AppVersionVO latestVersion = appVersionService.getLatestVersion();
        return RespBody.success(latestVersion);
    }
}
