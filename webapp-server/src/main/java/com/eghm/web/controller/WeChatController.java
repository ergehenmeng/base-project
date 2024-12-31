package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.dto.wechat.JsTicketDTO;
import com.eghm.dto.wechat.MaLoginDTO;
import com.eghm.dto.wechat.MaOpenLoginDTO;
import com.eghm.dto.wechat.MpLoginDTO;
import com.eghm.service.business.MemberService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.IpUtil;
import com.eghm.vo.login.LoginTokenVO;
import com.eghm.vo.wechat.JsTicketVO;
import com.eghm.wechat.WeChatMiniService;
import com.eghm.wechat.WeChatMpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author 二哥很猛
 * @since 2021/12/18 14:47
 */
@RestController
@AllArgsConstructor
@Api(tags = "微信授权")
@RequestMapping(value = "/webapp/wechat", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeChatController {

    private final MemberService memberService;

    private final WeChatMpService weChatMpService;

    private final WeChatMiniService weChatMiniService;

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

    @PostMapping(value = "/mp/jsTicket", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("微信公众号jsTicket签名")
    public RespBody<JsTicketVO> jsTicket(@Validated @RequestBody JsTicketDTO dto) {
        WxJsapiSignature signature = weChatMpService.jsTicket(dto.getUrl());
        return RespBody.success(DataUtil.copy(signature, JsTicketVO.class));
    }

    @GetMapping(value = "/ma/scene")
    @ApiOperation("解析小程序二维码参数")
    @ApiImplicitParam(name = "scene", value = "小程序码参数", required = true, dataType = "String", paramType = "query")
    public RespBody<String> scene(@RequestParam("scene") String scene) {
        String param = weChatMiniService.parseScene(scene);
        return RespBody.success(param);
    }
}
