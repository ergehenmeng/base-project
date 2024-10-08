package com.eghm.web.controller;

import com.eghm.constants.CommonConstant;
import com.eghm.utils.CacheUtil;
import com.eghm.utils.IpUtil;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 图形验证码controller
 * 方法支持 MathCaptchaProducer 与 TextCaptchaProducer
 *
 * @author 二哥很猛
 * @since 2018/1/19 11:50
 */
@RestController
@Slf4j
@Api(tags = "图形验证码")
@AllArgsConstructor
@RequestMapping("/manage")
public class CaptchaController {

    private final Producer producer;

    @GetMapping("/captcha")
    @ApiOperation("获取图形验证码")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authCode = producer.createText();
        String[] splits = authCode.split(CommonConstant.SPECIAL_SPLIT);
        String authImage = authCode;
        if (splits.length > 1) {
            authImage = splits[0];
            authCode = splits[1];
        }
        String ipAddress = IpUtil.getIpAddress(request);
        log.info("图形验证码[{}]:[{}]", ipAddress, authCode);
        CacheUtil.CAPTCHA_CACHE.put(ipAddress, authCode);
        BufferedImage bi = producer.createImage(authImage);
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        out.flush();
        out.close();
    }

}
