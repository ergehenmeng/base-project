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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 注册相关接口
 *
 * @author 二哥很猛
 * @date 2019/8/20 10:18
 */
@RestController
@Api(tags = "注册")
@AllArgsConstructor
public class RegisterController {

    private final UserService userService;

    /**
     * 注册发送短信验证码❶
     */
    @PostMapping("/register/send_sms")
    @ApiOperation("注册发送验证码")
    @SkipAccess
    public RespBody<Object> sendSms(@RequestBody @Valid RegisterSendSmsDTO request) {
        userService.registerSendSms(request.getMobile());
        return RespBody.success();
    }

    /**
     * 短信验证码校验及注册❷
     */
    @PostMapping("/register/user")
    @ApiOperation("短信注册用户")
    @SkipAccess
    public RespBody<LoginTokenVO> user(@RequestBody @Valid RegisterUserDTO request, HttpServletRequest servletRequest) {
        request.setChannel(ApiHolder.getChannel());
        request.setIp(IpUtil.getIpAddress(servletRequest));
        LoginTokenVO tokenVO = userService.registerByMobile(request);
        return RespBody.success(tokenVO);
    }

}
