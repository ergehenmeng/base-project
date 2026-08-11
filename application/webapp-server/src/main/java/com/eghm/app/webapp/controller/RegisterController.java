package com.eghm.app.webapp.controller;

import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.core.configuration.authentication.ApiHolder;
import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.web.utility.IpUtil;
import com.eghm.member.account.dto.AccountRegisterDTO;
import com.eghm.member.account.dto.MobileRegisterDTO;
import com.eghm.member.account.dto.RegisterSmsDTO;
import com.eghm.member.account.service.MemberService;
import com.eghm.member.account.vo.LoginTokenVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册相关接口
 *
 * @author 二哥很猛
 * @since 2019/8/20 10:18
 */
@RestController
@Tag(name = "注册")
@AllArgsConstructor
@RequestMapping(value = "/webapp/register", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegisterController {
    
    private final CacheService cacheService;

    private final MemberService memberService;

    @PostMapping(value = "/sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "注册发送验证码①")
    public RespBody<Void> sendSms(@RequestBody @Validated RegisterSmsDTO dto, HttpServletRequest request) {
        memberService.registerSendSms(dto.getMobile(), IpUtil.getIpAddress(request));
        return RespBody.success();
    }

    @PostMapping(value = "/mobile", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "手机号注册②")
    public RespBody<LoginTokenVO> mobile(@RequestBody @Validated MobileRegisterDTO request, HttpServletRequest servletRequest) {
        request.setChannel(ApiHolder.getChannel());
        request.setIp(IpUtil.getIpAddress(servletRequest));
        LoginTokenVO tokenVO = memberService.registerByMobile(request);
        return RespBody.success(tokenVO);
    }

    @PostMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "账号密码登录①")
    public RespBody<LoginTokenVO> account(@RequestBody @Validated AccountRegisterDTO request, HttpServletRequest servletRequest) {
        String sessionId = servletRequest.getSession().getId();
        String captchaKey = CacheConstant.CAPTCHA_KEY_PREFIX + sessionId;
        String captchaValue = cacheService.getValue(captchaKey);
        // 验证码使用后即为无效
        cacheService.delete(captchaKey);
        if (!request.getVerifyCode().equalsIgnoreCase(captchaValue)) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        request.setChannel(ApiHolder.getChannel());
        request.setIp(IpUtil.getIpAddress(servletRequest));
        LoginTokenVO tokenVO = memberService.registerByAccount(request);
        return RespBody.success(tokenVO);
    }
}