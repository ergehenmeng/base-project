package com.eghm.web.controller;

import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.register.RegisterSendSmsDTO;
import com.eghm.model.dto.register.RegisterUserDTO;
import com.eghm.model.vo.login.LoginTokenVO;
import com.eghm.service.user.UserService;
import com.eghm.utils.IpUtil;
import com.eghm.web.annotation.SkipAccess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 注册相关接口
 *
 * @author 二哥很猛
 * @date 2019/8/20 10:18
 */
@RestController
@Api(tags = "注册")
@AllArgsConstructor
@RequestMapping("/webapp/register")
public class RegisterController {

    private final UserService userService;

    @PostMapping("/sendSms")
    @ApiOperation("注册发送验证码①")
    @SkipAccess
    public RespBody<Void> sendSms(@RequestBody @Validated RegisterSendSmsDTO request) {
        userService.registerSendSms(request.getMobile());
        return RespBody.success();
    }

    @PostMapping("/user")
    @ApiOperation("短信注册用户②")
    @SkipAccess
    public RespBody<LoginTokenVO> user(@RequestBody @Validated RegisterUserDTO request, HttpServletRequest servletRequest) {
        request.setChannel(ApiHolder.getChannel());
        request.setIp(IpUtil.getIpAddress(servletRequest));
        LoginTokenVO tokenVO = userService.registerByMobile(request);
        return RespBody.success(tokenVO);
    }

}
