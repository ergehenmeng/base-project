package com.eghm.web.controller;

import com.eghm.model.dto.login.DeviceUnbindDTO;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.user.LoginDeviceVO;
import com.eghm.service.user.LoginDeviceService;
import com.eghm.web.annotation.SkipLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/5
 */
@RestController
@Api(tags = "登陆设备")
public class DeviceController {

    private LoginDeviceService loginDeviceService;

    @Autowired
    @SkipLogger
    public void setLoginDeviceService(LoginDeviceService loginDeviceService) {
        this.loginDeviceService = loginDeviceService;
    }

    /**
     * 用户登陆的设备列表
     */
    @ApiOperation("用户登陆过的设备列表")
    @GetMapping("/device/login_device")
    public RespBody<List<LoginDeviceVO>> loginDevice() {
        List<LoginDeviceVO> voList = loginDeviceService.getByUserId(ApiHolder.getUserId());
        return RespBody.success(voList);
    }

    /**
     * 解除设备绑定 (解绑后,用户必须通过手机验证码才能登陆)
     */
    @ApiOperation("解除设备绑定")
    @PostMapping("/device/unbind")
    public RespBody<Object> unbind(DeviceUnbindDTO request) {
        loginDeviceService.deleteLoginDevice(ApiHolder.getUserId(), request.getSerialNumber());
        return RespBody.success();
    }
}
