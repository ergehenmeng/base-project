package com.eghm.web.controller;

import com.eghm.constants.CommonConstant;
import com.google.code.kaptcha.Producer;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@Tag(name="图形验证码")
@AllArgsConstructor
@RequestMapping("/manage")
public class CaptchaController {

    private final Producer producer;

    @GetMapping("/captcha")
    @Operation(summary = "获取图形验证码")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authCode = producer.createText();
        String[] splits = authCode.split(CommonConstant.SPECIAL_SPLIT);
        String authImage = authCode;
        if (splits.length > 1) {
            authImage = splits[0];
            authCode = splits[1];
        }
        request.getSession().setAttribute(CommonConstant.CAPTCHA_KEY, authCode);
        log.info("图形验证码[{}]", authCode);
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
