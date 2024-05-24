package com.eghm.configuration;

import com.eghm.common.FileService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.common.impl.SystemFileServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 二哥很猛
 * @since 2024/5/24
 */

@Configuration
public class FileConfig {

    @Bean
    @ConditionalOnProperty(prefix = "system", name = "upload-type", havingValue = "system", matchIfMissing = true)
    public FileService fileService(SystemProperties systemProperties, SysConfigApi sysConfigApi) {
        return new SystemFileServiceImpl(systemProperties, sysConfigApi);
    }
}
