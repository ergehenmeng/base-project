package com.eghm.web.controller;

import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.login.DeviceUnbindDTO;
import com.eghm.model.vo.user.LoginDeviceVO;
import com.eghm.service.user.LoginDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/5
 */
@RestController
@Api(tags = "登陆设备")
@AllArgsConstructor
@RequestMapping("/webapp/user/device")
public class DeviceController {

    private final LoginDeviceService loginDeviceService;

    /**
     * 用户登陆的设备列表
     */
    @ApiOperation("用户登陆过的设备列表")
    @GetMapping("/loginDevice")
    public RespBody<List<LoginDeviceVO>> loginDevice() {
        List<LoginDeviceVO> voList = loginDeviceService.getByUserId(ApiHolder.getUserId());
        return RespBody.success(voList);
    }

    /**
     * 解除设备绑定 (解绑后,用户必须通过手机验证码才能登陆)
     */
    @ApiOperation("解除设备绑定")
    @PostMapping("/unbind")
    public RespBody<Void> unbind(@RequestBody @Valid  DeviceUnbindDTO request) {
        loginDeviceService.deleteLoginDevice(ApiHolder.getUserId(), request.getSerialNumber());
        return RespBody.success();
    }
}
