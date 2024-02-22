package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.dto.wechat.MaLoginDTO;
import com.eghm.dto.wechat.MaOpenLoginDTO;
import com.eghm.dto.wechat.MpLoginDTO;
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
 * @author 二哥很猛
 * @since 2021/12/18 14:47
 */
@RestController
@AllArgsConstructor
@Api(tags = "微信授权")
@RequestMapping("/webapp/wechat")
public class WeChatController {

    private final MemberService memberService;

    @PostMapping(value = "/mp/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("微信公众号授权登陆(自动注册)")
    public RespBody<LoginTokenVO> mpLogin(@Validated @RequestBody MpLoginDTO dto, HttpServletRequest request) {
        LoginTokenVO mpLogin = memberService.mpLogin(dto.getCode(), IpUtil.getIpAddress(request));
        return RespBody.success(mpLogin);
    }

    @PostMapping(value = "/ma/mobile/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("微信小程序授权手机号登陆")
    public RespBody<LoginTokenVO> maMobile(@Validated @RequestBody MaLoginDTO dto, HttpServletRequest request) {
        LoginTokenVO mpLogin = memberService.maLogin(dto.getCode(), dto.getOpenId(), IpUtil.getIpAddress(request));
        return RespBody.success(mpLogin);
    }

    /**
     * 2023.10月 微信小程序手机号授权登录需要收费,因此只需要第一次进行授权手机号登录,后续采用openId登录
     */
    @PostMapping(value = "/ma/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("微信小程序openId登录")
    public RespBody<LoginTokenVO> maLogin(@Validated @RequestBody MaOpenLoginDTO dto, HttpServletRequest request) {
        LoginTokenVO mpLogin = memberService.maLogin(dto.getOpenId(), IpUtil.getIpAddress(request));
        return RespBody.success(mpLogin);
    }
}
