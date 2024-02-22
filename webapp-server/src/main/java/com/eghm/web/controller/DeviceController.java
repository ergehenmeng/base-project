package com.eghm.web.controller;

import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.member.LoginService;
import com.eghm.vo.member.LoginDeviceVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "登陆设备")
@AllArgsConstructor
@RequestMapping("/webapp/member/device")
public class DeviceController {

    private final LoginService loginService;

    @ApiOperation("用户登陆过的设备列表")
    @GetMapping("/list")
    public RespBody<List<LoginDeviceVO>> list() {
        List<LoginDeviceVO> voList = loginService.getByMemberId(ApiHolder.getMemberId());
        return RespBody.success(voList);
    }

    @ApiOperation("解除设备绑定")
    @PostMapping(value = "/unbind", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> unbind(@RequestBody @Validated IdDTO dto) {
        loginService.deleteLoginDevice(ApiHolder.getMemberId(), dto.getId());
        return RespBody.success();
    }
}
