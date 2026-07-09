package com.eghm.infrastructure.integration.config;

import com.eghm.infrastructure.shared.configuration.properties.ApplicationProperties;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyuncs.exceptions.ClientException;
import com.eghm.domain.shared.service.AlarmService;
import com.eghm.application.shared.common.FileService;
import com.eghm.infrastructure.integration.common.impl.AliOssFileServiceImpl;
import com.eghm.application.shared.common.SysConfigService;
import com.eghm.infrastructure.integration.common.impl.SystemFileServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * oss 配置 阿里 + 本地
 *
 * @author 二哥很猛
 * @since 2024/5/24
 */

@Configuration
public class OssConfig {

    @Bean
    @ConditionalOnProperty(prefix = "system", name = "upload-type", havingValue = "system", matchIfMissing = true)
    public FileService systemFileService(ApplicationProperties applicationProperties, SysConfigService sysConfigService, AlarmService alarmService) {
        return new SystemFileServiceImpl(sysConfigService, alarmService, applicationProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "system", name = "upload-type", havingValue = "ali-oss")
    public FileService aliOssFileService(ApplicationProperties applicationProperties, AlarmService alarmService, SysConfigService sysConfigService) throws ClientException {
        return new AliOssFileServiceImpl(this.createClient(applicationProperties), sysConfigService, alarmService,
                applicationProperties);
    }

    /**
     * 阿里云oss客户端
     *
     * @param applicationProperties 配置oss
     * @return client
     * @throws ClientException e
     */
    private OSS createClient(ApplicationProperties applicationProperties) throws ClientException {
        ApplicationProperties.AliOss oss = applicationProperties.getAli().getOss();
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        return OSSClientBuilder.create()
                .endpoint(oss.getEndpoint())
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(oss.getRegionName())
                .build();
    }
}
