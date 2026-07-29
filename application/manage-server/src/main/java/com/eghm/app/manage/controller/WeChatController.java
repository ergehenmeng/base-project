package com.eghm.app.manage.controller;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.integration.wechat.dto.LinkUrlRequest;
import com.eghm.integration.wechat.dto.QrCodeRequest;
import com.eghm.integration.wechat.dto.ShortUrlRequest;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.enums.LoginType;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.platform.iam.entity.SysUser;
import com.eghm.platform.iam.service.SysUserService;
import com.eghm.platform.iam.vo.LoginResponse;
import com.eghm.platform.iam.vo.QrcodeLoginResponse;
import com.eghm.integration.wechat.service.WeChatMiniService;
import com.eghm.integration.wechat.service.WeChatMpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/3/26
 */
@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "微信相关")
@RequestMapping(value = "/manage/wechat", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeChatController {

    private final SysUserService sysUserService;

    private final WeChatMpService weChatMpService;

    private final WeChatMiniService weChatMiniService;
    
    private final ApplicationProperties applicationProperties;

    @PostMapping(value = "/shortUrl", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "小程序短链接")
    public RespBody<String> shortUrl(@RequestBody @Validated ShortUrlRequest request) {
        String shortUrl = weChatMiniService.generateShortUrl(request.getPageUrl(), request.getPageTitle(), request.getPersistent());
        return RespBody.success(shortUrl);
    }

    @PostMapping(value = "/linkUrl", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "小程序加密链接")
    public RespBody<String> linkUrl(@RequestBody @Validated LinkUrlRequest request) {
        String linkUrl = weChatMiniService.generateUrl(request.getPageUrl(), request.getQuery(), request.getValidDay());
        return RespBody.success(linkUrl);
    }

    @PostMapping(value = "/qrcode", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "小程序码")
    public RespBody<String> authMobile(@RequestBody @Validated QrCodeRequest request) {
        byte[] bytes = weChatMiniService.generateQrCode(request.getPageUrl(), request.getQuery(), request.getValidDay());
        return RespBody.success(ImgUtil.toBase64(ImgUtil.toImage(bytes), ImgUtil.IMAGE_TYPE_PNG));
    }

    @GetMapping("/qrcode/url")
    @Operation(summary = "获取PC扫码跳转地址")
    public RespBody<String> getQrConnectUrl(HttpSession session) {
        List<LoginType> typeList = applicationProperties.getManage().getLoginTypes();
        if (!typeList.contains(LoginType.QRCODE)) {
            throw new BusinessException(ErrorCode.QRCODE_NOT_SUPPORTED);
        }
        String state = IdUtil.fastSimpleUUID();
        session.setAttribute("state", state);
        return RespBody.success(weChatMpService.qrConnectUrl(state));
    }

    @GetMapping("/qrcode/login")
    @Operation(summary = "扫码成功回调")
    @Parameter(name = "state", description = "本地唯一state", required = true, in = ParameterIn.QUERY)
    @Parameter(name = "code", description = "微信返回code", required = true, in = ParameterIn.QUERY)
    public RespBody<QrcodeLoginResponse> callback(@RequestParam("state") String state, @RequestParam("code") String code, HttpSession session) {
        Object stateValue = session.getAttribute("state");
        QrcodeLoginResponse response = new QrcodeLoginResponse();
        if (stateValue == null || !stateValue.equals(state)) {
            log.error("state校验失败 本地:[{}] 入参:[{}]", stateValue, state);
            response.setState(0);
            return RespBody.success(response);
        }
        // 注意: 扫码后如果未绑定微信,则会跳转到登录页面,同时登录后自动绑定, 后续扫码后自动登录,如需更换微信,则需要解绑
        WxOAuth2AccessToken authed = weChatMpService.getAccessToken(code);
        SysUser sysUser = sysUserService.getByOpenId(authed.getOpenId());
        if (sysUser == null) {
            log.warn("微信扫码尚未绑定账号 [{}]", authed.getOpenId());
            session.setAttribute(CommonConstant.OPEN_ID, authed.getOpenId());
            response.setState(0);
            return RespBody.success(response);
        }
        LoginResponse doneLogin = sysUserService.doLogin(sysUser);
        response.setData(doneLogin);
        response.setState(1);
        return RespBody.success(response);
    }
}
