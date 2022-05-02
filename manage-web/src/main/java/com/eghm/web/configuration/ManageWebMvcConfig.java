package com.eghm.web.configuration;

import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.WebMvcConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

/**
 * mvc配置信息
 *
 * @author 二哥很猛
 * @date 2018/1/18 18:35
 */
@Configuration
public class ManageWebMvcConfig extends WebMvcConfig {

    public ManageWebMvcConfig(ObjectMapper objectMapper, SystemProperties systemProperties) {
        super(objectMapper, systemProperties);
    }
}
