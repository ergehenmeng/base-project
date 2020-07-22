package com.eghm.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 极光推送
 * @author 二哥很猛
 * @date 2019/8/29 11:39
 */
@Data
@PropertySource("classpath:push.properties")
@Component
public class PushProperties implements Serializable {

    private static final long serialVersionUID = 1573451262587043015L;

    /**
     * 推送地址
     */
    @Value("${push-url:''}")
    private String url;

    /**
     * true:生产环境 false:测试环境
     */
    @Value("${env:false}")
    private Boolean env;

    /**
     * 推送key
     */
    @Value("${master-secret:''}")
    private String masterSecret;

    /**
     * 推送appKey
     */
    @Value("${app-key:''}")
    private String appKey;

}
