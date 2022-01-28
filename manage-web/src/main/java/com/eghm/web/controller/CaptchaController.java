package com.eghm.web.controller;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.lang.UUID;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.login.CaptchaResponse;
import com.eghm.service.cache.CacheService;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class CaptchaController {

    private Producer producer;

    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Autowired
    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    /**
     * 默认的图片验证码
     * 注意: 该方式可能会被人攻击,未做ip调用次数限制
     */
    @GetMapping("/captcha")
    @ApiOperation("获取图形验证码")
    public RespBody<CaptchaResponse> captcha() {
        String value = producer.createText();
        String key = UUID.randomUUID().toString();
        log.info("图形验证码[{}]:[{}]", key, value);
        cacheService.setValue(key, value, 600000L);
        BufferedImage bi = producer.createImage(value);
        String base64 = ImgUtil.toBase64(bi, "jpg");
        CaptchaResponse response = new CaptchaResponse();
        response.setKey(key);
        response.setBase64(base64);
        return RespBody.success(response);
    }

}
