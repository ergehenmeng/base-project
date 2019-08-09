package com.fanyin.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author 二哥很猛
 * @date 2018/7/2 14:05
 */
@ConfigurationProperties(prefix = ApplicationProperties.PREFIX)
@Component
@Data
public class ApplicationProperties {

    protected static final String PREFIX = "application";

    /**
     * 系统版本号
     */
    private String version;

    /**
     * 未登陆忽略,不拦截的地址
     */
    private String ignoreUrl;

    /**
     * 已登录 忽略的地址
     */
    private String loginIgnoreUrl;

    /**
     * 上传文件保存目录
     */
    private String uploadDir;
}
