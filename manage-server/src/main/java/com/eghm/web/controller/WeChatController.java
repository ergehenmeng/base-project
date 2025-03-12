package com.eghm.web.controller;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.wechat.*;
import com.eghm.enums.ErrorCode;
import com.eghm.model.SysUser;
import com.eghm.service.sys.SysUserService;
import com.eghm.vo.login.LoginResponse;
import com.eghm.wechat.WeChatMiniService;
import com.eghm.wechat.WeChatMpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author 二哥很猛
 * @since 2024/3/26
 */
@Slf4j
@RestController
@Api(tags = "微信相关")
@AllArgsConstructor
@RequestMapping(value = "/manage/wechat", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeChatController {

    private final SysUserService sysUserService;

    private final WeChatMpService weChatMpService;

    private final WeChatMiniService weChatMiniService;

    @PostMapping(value = "/shortUrl", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("小程序短链接")
    public RespBody<String> shortUrl(@RequestBody @Validated ShortUrlRequest request) {
        String shortUrl = weChatMiniService.generateShortUrl(request.getPageUrl(), request.getPageTitle(), request.getPersistent());
        return RespBody.success(shortUrl);
    }

    @PostMapping(value = "/linkUrl", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("小程序加密链接")
    public RespBody<String> linkUrl(@RequestBody @Validated LinkUrlRequest request) {
        String linkUrl = weChatMiniService.generateUrl(request.getPageUrl(), request.getQuery(), request.getValidDay());
        return RespBody.success(linkUrl);
    }

    @PostMapping(value = "/qrcode", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("小程序码")
    public RespBody<String> authMobile(@RequestBody @Validated QrCodeRequest request) {
        byte[] bytes = weChatMiniService.generateQrCode(request.getPageUrl(), request.getQuery(), request.getValidDay());
        return RespBody.success(ImgUtil.toBase64(ImgUtil.toImage(bytes), ImgUtil.IMAGE_TYPE_PNG));
    }

    @GetMapping(value = "/qrcode/url")
    @ApiOperation("获取PC扫码跳转地址")
    public RespBody<String> getQrConnectUrl(HttpSession session) {
        String state = IdUtil.fastSimpleUUID();
        session.setAttribute("state", state);
        return RespBody.success(weChatMpService.qrConnectUrl(state));
    }

    @GetMapping(value = "/qrcode/login")
    @ApiOperation("扫码成功回调")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state", value = "本地唯一state", required = true),
            @ApiImplicitParam(name = "code", value = "微信返回code", required = true)
    })
    public RespBody<LoginResponse> callback(@RequestParam("state") String state, @RequestParam("code") String code, HttpSession session) {
        Object stateValue = session.getAttribute("state");
        if (stateValue == null || !stateValue.equals(state)) {
            log.error("state校验失败 本地:[{}] 入参:[{}]", stateValue, state);
            return RespBody.error(ErrorCode.AUTH_ERROR);
        }
        WxOAuth2AccessToken authed = weChatMpService.getAccessToken(code);
        SysUser sysUser = sysUserService.getByMpOpenId(authed.getOpenId());
        if (sysUser == null) {
            log.warn("微信扫码尚未绑定账号 [{}]", authed.getOpenId());
            session.setAttribute("openId", authed.getOpenId());
            return RespBody.error(ErrorCode.AUTH_BIND_ERROR);
        }
        LoginResponse response = sysUserService.doLogin(sysUser);
        return RespBody.success(response);
    }

    /**
     * 2023.10月 微信小程序手机号授权登录需要收费,因此只需要第一次进行授权手机号登录,后续采用openId登录
     */
    @PostMapping(value = "/mobile/login")
    @ApiOperation("小程序手机号授权登录")
    public RespBody<LoginResponse> authLogin(@RequestBody @Validated MaMobileLoginRequest request) {
        String mobile = weChatMiniService.authMobile(request.getCode());
        LoginResponse response = sysUserService.maLogin(mobile, request.getOpenId());
        return RespBody.success(response);
    }

    /**
     * 2023.10月 微信小程序手机号授权登录需要收费,因此只需要第一次进行授权手机号登录,后续采用openId登录
     */
    @PostMapping(value = "/code/login")
    @ApiOperation("小程序openId授权登录")
    public RespBody<LoginResponse> authLogin(@RequestBody @Validated MaOpenLoginRequest request) {
        String openId = weChatMiniService.getOpenId(request.getCode());
        LoginResponse response = sysUserService.openIdLogin(openId);
        return RespBody.success(response);
    }
}
