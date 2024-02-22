package com.eghm.web.controller;

import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.register.RegisterMemberDTO;
import com.eghm.dto.register.RegisterSendSmsDTO;
import com.eghm.service.member.MemberService;
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
 *
 * @author 二哥很猛
 * @since 2019/8/20 10:18
 */
@RestController
@Api(tags = "注册")
@AllArgsConstructor
@RequestMapping("/webapp/register")
public class RegisterController {

    private final MemberService memberService;

    @PostMapping(value = "/sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("注册发送验证码①")
    public RespBody<Void> sendSms(@RequestBody @Validated RegisterSendSmsDTO dto, HttpServletRequest request) {
        memberService.registerSendSms(dto.getMobile(), IpUtil.getIpAddress(request));
        return RespBody.success();
    }

    @PostMapping(value = "/member", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("短信注册用户②")
    public RespBody<LoginTokenVO> member(@RequestBody @Validated RegisterMemberDTO request, HttpServletRequest servletRequest) {
        request.setChannel(ApiHolder.getChannel());
        request.setIp(IpUtil.getIpAddress(servletRequest));
        LoginTokenVO tokenVO = memberService.registerByMobile(request);
        return RespBody.success(tokenVO);
    }

}
