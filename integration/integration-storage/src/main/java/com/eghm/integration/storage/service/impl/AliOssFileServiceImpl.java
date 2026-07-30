package com.eghm.integration.storage.service.impl;

import com.aliyun.oss.OSS;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.constants.ConfigConstant;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.foundation.core.service.AlarmService;
import com.eghm.foundation.web.utility.CacheUtil;
import com.eghm.integration.storage.dto.FilePath;
import com.eghm.integration.storage.service.FileService;
import com.eghm.platform.config.service.SysConfigApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

import static com.eghm.foundation.core.constants.CommonConstant.DAY_MAX_UPLOAD;

/**
 * @author 二哥很猛
 * @since 2024/11/22
 */

@Slf4j
@AllArgsConstructor
public class AliOssFileServiceImpl implements FileService {

    private final OSS ossClient;

    private final SysConfigApi sysConfigApi;

    private final AlarmService alarmService;

    private final ApplicationProperties applicationProperties;

    @Override
    public FilePath saveFile(String key, MultipartFile file) {
        return this.saveFile(key, file, applicationProperties.getStorage().getFolder(), sysConfigApi.getLong(ConfigConstant.SINGLE_MAX_FILE_SIZE));
    }

    @Override
    public FilePath saveFile(String key, MultipartFile file, String folder) {
        return this.saveFile(key, file, folder, sysConfigApi.getLong(ConfigConstant.SINGLE_MAX_FILE_SIZE));
    }

    @Override
    public FilePath saveFile(String key, MultipartFile file, String folder, long maxSize) {
        this.checkSize(file, maxSize);
        Long present = CacheUtil.UPLOAD_LIMIT_CACHE.getIfPresent(key);
        long size = file.getSize() + (present == null ? 0 : present);
        if (size > DAY_MAX_UPLOAD.toBytes()) {
            log.warn("ALI_OSS单日上传文件超出限制, 用户:[{}] 累计上传:[{}]kb ", key, size / 1024);
            alarmService.sendMsg(String.format("ALI_OSS单日上传文件超出限制,请注意监控, 用户:%s 今日累计上传:%s", key, (size / 1024 / 1024) + "M"));
        }
        FilePath filePath = this.doUploadFile(file, folder);
        CacheUtil.UPLOAD_LIMIT_CACHE.put(key, size);
        return filePath;
    }

    /**
     * oss 上传文件
     *
     * @param file   file
     * @param folder 父文件夹
     * @return 上传后的文件路径
     */
    private FilePath doUploadFile(MultipartFile file, String folder) {
        String filePath = this.generateRelativePath(file, folder);
        try (InputStream inputStream = file.getInputStream()) {
            ossClient.putObject(applicationProperties.getStorage().getAli().getBucketName(), filePath, inputStream);
        } catch (Exception e) {
            log.error("ALI_OSS文件上传失败, 文件名:[{}]", filePath, e);
            throw new BusinessException(ErrorCode.FILE_SAVE_ERROR);
        }
        return new FilePath(filePath.replace(File.separator, "/"), applicationProperties.getStorage().getAli().getAccessDomain(), file.getSize());
    }

}
