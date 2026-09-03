package com.eghm.integration.storage.service.impl;

import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.constants.ConfigConstant;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.foundation.core.service.AlarmService;
import com.eghm.integration.storage.dto.FilePath;
import com.eghm.integration.storage.service.StorageService;
import com.eghm.platform.config.service.SysConfigApi;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * @author wyb-eghm
 * @since 2026/7/30
 */
@Slf4j
@RequiredArgsConstructor
public class MinioServiceImpl implements StorageService {
    
    private final MinioClient minioClient;
    
    private final SysConfigApi sysConfigApi;
    
    private final AlarmService alarmService;
    
    private final ApplicationProperties applicationProperties;
    
    @Override
    public FilePath saveFile(MultipartFile file) {
        return this.saveFile(file, applicationProperties.getStorage().getFolder(), sysConfigApi.getLong(ConfigConstant.SINGLE_MAX_FILE_SIZE));
    }
    
    @Override
    public FilePath saveFile(MultipartFile file, String folder) {
        return this.saveFile(file, folder, sysConfigApi.getLong(ConfigConstant.SINGLE_MAX_FILE_SIZE));
    }
    
    @Override
    public FilePath saveFile(MultipartFile file, String folder, long maxSize) {
        return this.checkAndSaveFile(file, folder, maxSize, alarmService);
    }
    
    @Override
    public FilePath doSaveFile(MultipartFile file, String filePath) {
        ApplicationProperties.StorageProperties.MinioStorage minio = applicationProperties.getStorage().getMinio();
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                            .bucket(minio.getBucketName())
                            .object(filePath)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            log.error("MinIO文件上传失败, 文件名:[{}]", filePath, e);
            throw new BusinessException(ErrorCode.FILE_SAVE_ERROR);
        }
        String host = minio.getEndpoint() + "/" + minio.getBucketName();
        return new FilePath(filePath.replace(File.separator, "/"), host, file.getSize());
    }
}