package com.eghm.integration.storage.config;

import com.eghm.foundation.core.configuration.ApplicationProperties;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyuncs.exceptions.ClientException;
import com.eghm.foundation.core.service.AlarmService;
import com.eghm.integration.storage.service.StorageService;
import com.eghm.integration.storage.service.impl.AliOssStorageServiceImpl;
import com.eghm.platform.config.service.SysConfigApi;
import com.eghm.integration.storage.service.impl.LocalStorageServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件存储配置 阿里 + 本地
 *
 * @author 二哥很猛
 * @since 2024/5/24
 */

@Configuration
public class StorageConfig {

    @Bean
    @ConditionalOnProperty(prefix = "application.storage", name = "type", havingValue = "local", matchIfMissing = true)
    public StorageService systemFileService(ApplicationProperties applicationProperties, SysConfigApi sysConfigApi, AlarmService alarmService) {
        return new LocalStorageServiceImpl(sysConfigApi, alarmService, applicationProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "application.storage", name = "type", havingValue = "ali-oss")
    public StorageService aliOssFileService(ApplicationProperties applicationProperties, AlarmService alarmService, SysConfigApi sysConfigApi) throws ClientException {
        return new AliOssStorageServiceImpl(this.createClient(applicationProperties), sysConfigApi, alarmService, applicationProperties);
    }

    /**
     * 阿里云oss客户端
     *
     * @param applicationProperties 配置oss
     * @return client
     * @throws ClientException e
     */
    private OSS createClient(ApplicationProperties applicationProperties) throws ClientException {
        ApplicationProperties.StorageProperties.AliStorage oss = applicationProperties.getStorage().getAli();
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
