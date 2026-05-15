package com.eghm.i18n.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "i18n")
public class I18nProperties {

    private boolean enabled = true;
    
    /**
     * 语言头名称
     */
    private String headerName = "X-Language";

}
