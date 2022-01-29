package com.eghm.web.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.MD5;
import com.eghm.common.constant.CacheConstant;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.login.CaptchaResponse;
import com.eghm.service.cache.CacheService;
import com.eghm.utils.IpUtil;
import com.eghm.utils.WebUtil;
import com.google.code.kaptcha.Producer;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;

/**
 * 图形验证码controller
 *
 * @author 二哥很猛
 * @date 2018/1/19 11:50
 */
@RestController
@Slf4j
@Api(tags = "图形验证码")
@AllArgsConstructor
public class CaptchaController {

    private Producer producer;

    private CacheService cacheService;

    /**
     * 默认的图片验证码
     * 采用ip方式作为key
     */
    @GetMapping("/captcha")
    @ApiOperation("获取图形验证码")
    public RespBody<CaptchaResponse> captcha(HttpServletRequest request) {
        String value = producer.createText();
        String key = UUID.randomUUID().toString();
        log.info("图形验证码[{}]:[{}]", key, value);
        String ipAddress = IpUtil.getIpAddress(request);
        cacheService.setValue(CacheConstant.IMAGE_CAPTCHA + ipAddress, value, 600000L);
        BufferedImage bi = producer.createImage(value);
        String base64 = ImgUtil.toBase64(bi, "png");
        CaptchaResponse response = new CaptchaResponse();
        response.setBase64(base64);
        return RespBody.success(response);
    }
}
