package com.eghm.integration.storage.service.impl;

import com.aliyun.oss.OSS;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.constants.ConfigConstant;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.foundation.core.service.AlarmService;
import com.eghm.integration.storage.dto.FilePath;
import com.eghm.integration.storage.service.StorageService;
import com.eghm.platform.config.service.SysConfigApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * @author 二哥很猛
 * @since 2024/11/22
 */

@Slf4j
@AllArgsConstructor
public class AliOssStorageServiceImpl implements StorageService {

    private final OSS ossClient;

    private final SysConfigApi sysConfigApi;

    private final AlarmService alarmService;

    private final ApplicationProperties applicationProperties;

    @Override
    public FilePath saveFile(MultipartFile file) {
        return this.saveFile(file, applicationProperties.getStorage().getFolder(), sysConfigApi.getLong(ConfigConstant.SINGLE_MAX_FILE_SIZE));
    }

    @Override
    public FilePath saveFile(MultipartFile file, String folder) {
        return this.saveFile(file, folder);
    }

    @Override
    public FilePath saveFile(MultipartFile file, String folder, long maxSize) {
        return this.checkAndSaveFile(file, folder, maxSize, alarmService);
    }

    @Override
    public FilePath doSaveFile(MultipartFile file, String filePath) {
        try (InputStream inputStream = file.getInputStream()) {
            ossClient.putObject(applicationProperties.getStorage().getAli().getBucketName(), filePath, inputStream);
        } catch (Exception e) {
            log.error("ALI_OSS文件上传失败, 文件名:[{}]", filePath, e);
            throw new BusinessException(ErrorCode.FILE_SAVE_ERROR);
        }
        return new FilePath(filePath.replace(File.separator, "/"), applicationProperties.getStorage().getAli().getAccessDomain(), file.getSize());
    }

}
