package com.eghm.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author 二哥很猛
 * @date 2018/7/2 14:05
 */
@ConfigurationProperties(prefix = ApplicationProperties.PREFIX)
@Component
public class ApplicationProperties {

    static final String PREFIX = "application";

    /**
     * 系统版本号
     */
    private String version;

    /**
     * 未登陆忽略,不拦截的地址 (一般是登陆页面)
     */
    private String[] ignoreUrl = new String[]{};

    /**
     * 已登录 忽略的地址 (即所有用户登陆后均可以看到的页面url)
     */
    private String[] loginIgnoreUrl = new String[]{};

    /**
     * 上传文件保存的文件夹目录
     */
    private String uploadDir;

    /**
     * 加密串,用于需要在数据库加密时
     */
    private String secretKey = "Mfw7MfWbaUbvz7X3aKqItx94oAUQH60FiKXe65QN3cUTbwJ2V2ux6ilf361yEU==";


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[] getIgnoreUrl() {
        return ignoreUrl;
    }

    public void setIgnoreUrl(String... ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }

    public String[] getLoginIgnoreUrl() {
        return loginIgnoreUrl;
    }

    public void setLoginIgnoreUrl(String... loginIgnoreUrl) {
        this.loginIgnoreUrl = loginIgnoreUrl;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
