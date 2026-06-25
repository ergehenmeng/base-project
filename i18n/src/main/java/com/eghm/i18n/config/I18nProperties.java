package com.eghm.i18n.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 国际化配置类
 * @author wyb-eghm
 * @since 2026/5/15
 */
@Data
@ConfigurationProperties(prefix = "i18n")
public class I18nProperties {
    
    /**
     * 是否开启i18n翻译
     */
    private boolean enabled = true;
    
    /**
     * 请求头语言名称 默认值：X-Language, 注意: 如果请求头中没有该字段, 则使用Accept-Language字段
     */
    private String headerName = "X-Language";

}
