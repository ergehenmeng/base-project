package com.eghm.interfaces.webapp.controller;

import com.eghm.dto.IdDTO;
import com.eghm.configuration.authentication.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.application.member.service.LoginService;
import com.eghm.vo.business.member.LoginDeviceVO;
import com.eghm.interfaces.webapp.annotation.AccessToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/5
 */
@AccessToken
@RestController
@Tag(name = "登陆设备")
@AllArgsConstructor
@RequestMapping(value = "/webapp/member/device", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {

    private final LoginService loginService;

    @Operation(summary = "用户登陆过的设备列表")
    @GetMapping("/list")
    public RespBody<List<LoginDeviceVO>> list() {
        List<LoginDeviceVO> voList = loginService.getByMemberId(ApiHolder.getMemberId());
        return RespBody.success(voList);
    }

    @Operation(summary = "解除设备绑定")
    @PostMapping(value = "/unbind", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> unbind(@RequestBody @Validated IdDTO dto) {
        loginService.deleteLoginDevice(ApiHolder.getMemberId(), dto.getId());
        return RespBody.success();
    }
}
