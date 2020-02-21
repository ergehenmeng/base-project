package com.eghm.controller;

import com.eghm.model.dto.register.RegisterSendSmsRequest;
import com.eghm.model.dto.register.RegisterUserRequest;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.model.ext.RespBody;
import com.eghm.model.vo.login.LoginTokenVO;
import com.eghm.service.user.UserService;
import com.eghm.utils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 注册相关接口
 *
 * @author 二哥很猛
 * @date 2019/8/20 10:18
 */
@RestController
@Api("注册")
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * 注册发送短信验证码❶
     */
    @PostMapping("/register/send_sms")
    @ApiOperation("注册发送验证码")
    public RespBody<Object> sendSms(RegisterSendSmsRequest request) {
        userService.registerSendSms(request.getMobile());
        return RespBody.getInstance();
    }

    /**
     * 短信验证码校验及注册❷
     */
    @PostMapping("/register/user")
    @ApiOperation("短信注册用户")
    public LoginTokenVO user(HttpServletRequest servletRequest, RegisterUserRequest request) {
        request.setChannel(RequestThreadLocal.getChannel());
        request.setIp(IpUtil.getIpAddress(servletRequest));
        return userService.registerByMobile(request);
    }

}
