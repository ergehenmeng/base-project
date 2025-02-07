package com.eghm.web.controller;

import com.eghm.dto.ext.RespBody;
import com.eghm.utils.SpringContextUtil;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2025/2/7
 */
@Slf4j
@Hidden
@RestController
public class ShutdownHookController {

    @PostMapping(value = "/webapp/showdown")
    public RespBody<Void> showdown(String secret) {
        if (ShutdownHookController.class.getName().equals(secret)) {
            ((ConfigurableApplicationContext) SpringContextUtil.getApplicationContext()).close();
        }
        return RespBody.success();
    }
}
