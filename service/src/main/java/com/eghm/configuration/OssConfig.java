package com.eghm.configuration;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyuncs.exceptions.ClientException;
import com.eghm.common.AlarmService;
import com.eghm.common.FileService;
import com.eghm.common.impl.AliOssFileServiceImpl;
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
public class OssConfig {

    @Bean
    @ConditionalOnProperty(prefix = "system", name = "upload-type", havingValue = "system", matchIfMissing = true)
    public FileService systemFileService(SystemProperties systemProperties, SysConfigApi sysConfigApi, AlarmService alarmService) {
        return new SystemFileServiceImpl(sysConfigApi, alarmService, systemProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "system", name = "upload-type", havingValue = "ali-oss")
    public FileService aliOssFileService(SystemProperties systemProperties, AlarmService alarmService, SysConfigApi sysConfigApi) throws ClientException {
        return new AliOssFileServiceImpl(this.createClient(systemProperties), sysConfigApi, alarmService, systemProperties);
    }

    /**
     * 阿里云oss客户端
     *
     * @param systemProperties 配置oss
     * @return client
     * @throws ClientException e
     */
    private OSS createClient(SystemProperties systemProperties) throws ClientException {
        SystemProperties.AliOss oss = systemProperties.getAli().getOss();
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
