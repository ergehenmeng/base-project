package com.eghm.web.controller;

import com.eghm.constants.CommonConstant;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sys.register.AccountRegisterDTO;
import com.eghm.dto.sys.register.MobileRegisterDTO;
import com.eghm.dto.sys.register.RegisterSmsDTO;
import com.eghm.enums.ErrorCode;
import com.eghm.service.business.MemberService;
import com.eghm.utils.IpUtil;
import com.eghm.vo.login.LoginTokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 注册相关接口

 * @author 二哥很猛
 * @since 2019/8/20 10:18
 */
@RestController
@Api(tags = "注册")
@AllArgsConstructor
@RequestMapping(value = "/webapp/register", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegisterController {

    private final MemberService memberService;

    @PostMapping(value = "/sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("注册发送验证码①")
    public RespBody<Void> sendSms(@RequestBody @Validated RegisterSmsDTO dto, HttpServletRequest request) {
        memberService.registerSendSms(dto.getMobile(), IpUtil.getIpAddress(request));
        return RespBody.success();
    }

    @PostMapping(value = "/mobile", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("手机号注册②")
    public RespBody<LoginTokenVO> mobile(@RequestBody @Validated MobileRegisterDTO request, HttpServletRequest servletRequest) {
        request.setChannel(ApiHolder.getChannel());
        request.setIp(IpUtil.getIpAddress(servletRequest));
        LoginTokenVO tokenVO = memberService.registerByMobile(request);
        return RespBody.success(tokenVO);
    }

    @PostMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("账号密码登录①")
    public RespBody<LoginTokenVO> account(@RequestBody @Validated AccountRegisterDTO request, HttpServletRequest servletRequest) {
        Object value = servletRequest.getSession().getAttribute(CommonConstant.CAPTCHA_KEY);
        // 验证码使用后即为无效
        servletRequest.getSession().removeAttribute(CommonConstant.CAPTCHA_KEY);
        if (value == null || !request.getVerifyCode().equalsIgnoreCase(value.toString())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        request.setChannel(ApiHolder.getChannel());
        request.setIp(IpUtil.getIpAddress(servletRequest));
        LoginTokenVO tokenVO = memberService.registerByAccount(request);
        return RespBody.success(tokenVO);
    }
}
