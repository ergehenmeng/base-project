package com.eghm.web.controller;

import com.eghm.constant.CacheConstant;
import com.eghm.service.cache.CacheService;
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
 *
 * @author 二哥很猛
 * @date 2018/1/19 11:50
 */
@RestController
@Slf4j
@Api(tags = "图形验证码")
@AllArgsConstructor
@RequestMapping("/manage")
public class CaptchaController {

    private final Producer producer;

    private final CacheService cacheService;

    @GetMapping("/captcha")
    @ApiOperation("获取图形验证码")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String value = producer.createText();
        String ipAddress = IpUtil.getIpAddress(request);
        log.info("图形验证码[{}]:[{}]", ipAddress, value);
        cacheService.setValue(CacheConstant.IMAGE_CAPTCHA + ipAddress, value, 60000L);
        BufferedImage bi = producer.createImage(value);
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        out.flush();
        out.close();
    }

}
