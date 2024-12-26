package com.eghm.common.impl;

import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSS;
import com.eghm.common.AlarmService;
import com.eghm.common.FileService;
import com.eghm.configuration.SystemProperties;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.FilePath;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.utils.CacheUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import static com.eghm.constants.CommonConstant.DAY_MAX_UPLOAD;

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

    private final SystemProperties systemProperties;

    @Override
    public FilePath saveFile(String key, MultipartFile file) {
        return this.saveFile(key, file, systemProperties.getUploadFolder(), sysConfigApi.getLong(ConfigConstant.SINGLE_MAX_FILE_SIZE));
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
     * 生成文件名 + 文件后缀
     *
     * @param file 原文件
     * @param folder 文件夹
     * @return folder/uuid.txt
     */
    private String generateFileName(MultipartFile file, String folder) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new BusinessException(ErrorCode.FILE_NAME_NOT_FOUND);
        }
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index);
        return folder + "/" + UUID.randomUUID() + suffix;
    }

    /**
     * oss 上传文件
     *
     * @param file file
     * @param folder 父文件夹
     * @return 上传后的文件路径
     */
    private FilePath doUploadFile(MultipartFile file, String folder) {
        String fileName = this.generateFileName(file, folder);
        try (InputStream inputStream = file.getInputStream()){
            ossClient.putObject(systemProperties.getAli().getOss().getBucketName(), fileName, inputStream);
        } catch (Exception e) {
            log.error("ALI_OSS文件上传失败, 文件名:[{}]", fileName, e);
            throw new BusinessException(ErrorCode.FILE_SAVE_ERROR);
        }
        return FilePath.builder().path(fileName).address(systemProperties.getAli().getOss().getAccessDomain()).size(file.getSize()).build();
    }

}
