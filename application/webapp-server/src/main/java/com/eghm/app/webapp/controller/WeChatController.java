package com.eghm.app.webapp.controller;

import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.integration.wechat.dto.JsTicketDTO;
import com.eghm.integration.wechat.dto.MaLoginDTO;
import com.eghm.integration.wechat.dto.MaOpenLoginDTO;
import com.eghm.integration.wechat.dto.MpLoginDTO;
import com.eghm.member.account.service.MemberService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.foundation.web.utility.IpUtil;
import com.eghm.member.account.vo.LoginTokenVO;
import com.eghm.integration.wechat.vo.JsTicketVO;
import com.eghm.integration.wechat.service.WeChatMiniService;
import com.eghm.integration.wechat.service.WeChatMpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2021/12/18 14:47
 */
@RestController
@AllArgsConstructor
@Tag(name = "微信授权")
@RequestMapping(value = "/webapp/wechat", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeChatController {

    private final MemberService memberService;

    private final WeChatMpService weChatMpService;

    private final WeChatMiniService weChatMiniService;

    @PostMapping(value = "/mp/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "微信公众号授权登陆(自动注册)")
    public RespBody<LoginTokenVO> mpLogin(@Validated @RequestBody MpLoginDTO dto, HttpServletRequest request) {
        LoginTokenVO mpLogin = memberService.mpLogin(dto.getCode(), IpUtil.getIpAddress(request));
        return RespBody.success(mpLogin);
    }

    @PostMapping(value = "/ma/mobile/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "微信小程序授权手机号登陆")
    public RespBody<LoginTokenVO> maMobile(@Validated @RequestBody MaLoginDTO dto, HttpServletRequest request) {
        LoginTokenVO mpLogin = memberService.maLogin(dto.getCode(), dto.getOpenId(), IpUtil.getIpAddress(request));
        return RespBody.success(mpLogin);
    }

    /**
     * 2023.10月 微信小程序手机号授权登录需要收费,因此只需要第一次进行授权手机号登录,后续采用openId登录
     */
    @PostMapping(value = "/ma/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "微信小程序openId登录")
    public RespBody<LoginTokenVO> maLogin(@Validated @RequestBody MaOpenLoginDTO dto, HttpServletRequest request) {
        LoginTokenVO mpLogin = memberService.maLogin(dto.getOpenId(), IpUtil.getIpAddress(request));
        return RespBody.success(mpLogin);
    }

    @PostMapping(value = "/mp/jsTicket", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "微信公众号jsTicket签名")
    public RespBody<JsTicketVO> jsTicket(@Validated @RequestBody JsTicketDTO dto) {
        WxJsapiSignature signature = weChatMpService.jsTicket(dto.getUrl());
        return RespBody.success(DataUtil.copy(signature, JsTicketVO.class));
    }

    @GetMapping("/ma/scene")
    @Operation(summary = "解析小程序二维码参数")
    @Parameter(name = "scene", description = "小程序码参数", required = true, in = ParameterIn.QUERY)
    public RespBody<String> scene(@RequestParam("scene") String scene) {
        String param = weChatMiniService.parseScene(scene);
        return RespBody.success(param);
    }
}
