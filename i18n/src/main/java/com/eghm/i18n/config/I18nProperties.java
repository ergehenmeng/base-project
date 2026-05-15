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

    private boolean enabled = true;
    
    /**
     * 语言头名称
     */
    private String headerName = "X-Language";

}
