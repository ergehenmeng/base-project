package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.service.operate.AppVersionService;
import com.eghm.vo.operate.version.AppVersionVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "更新版本")
@AllArgsConstructor
@RequestMapping(value = "/webapp/version", produces = MediaType.APPLICATION_JSON_VALUE)
public class VersionController {

    private final AppVersionService appVersionService;

    @GetMapping("/latest")
    @Operation(summary = "获取最新的版本")
    public RespBody<AppVersionVO> latest() {
        AppVersionVO latestVersion = appVersionService.getLatestVersion();
        return RespBody.success(latestVersion);
    }
}
