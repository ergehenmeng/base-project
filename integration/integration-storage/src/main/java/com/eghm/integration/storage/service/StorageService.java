package com.eghm.integration.storage.service;

import cn.hutool.core.util.IdUtil;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.foundation.core.service.AlarmService;
import com.eghm.integration.storage.dto.FilePath;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2019/11/15 14:49
 */
public interface StorageService {

    /**
     * 保存文件
     *
     * @param file 文件
     * @return 文件保存的相对路径
     */
    FilePath saveFile(MultipartFile file);

    /**
     * 保存文件
     *
     * @param file 文件
     * @param folder 文件保存的文件夹名称
     * @return 文件保存的相对路径
     */
    FilePath saveFile(MultipartFile file, String folder);

    /**
     * 保存文件
     *
     * @param file 文件
     * @param folder 文件保存的文件夹名称 (主路径由全局定义,父级文件夹可在此处自定义)
     * @param maxSize 文件最大限制 byte
     * @return 文件保存的相对路径
     */
    FilePath saveFile(MultipartFile file, String folder, long maxSize);
    
    /**
     * 校验用户累计上传文件大小并保存文件
     *
     * @param file 文件
     * @param folder 文件保存的文件夹名称
     * @param maxSize 文件最大限制 byte - 只报警,不抛出异常
     * @param alarmService 报警服务
     * @return 文件保存的相对路径
     */
    default FilePath checkAndSaveFile(MultipartFile file, String folder, long maxSize, AlarmService alarmService) {
        this.checkSize(file, maxSize);
        String path = this.generateRelativePath(file, folder);
        return this.doSaveFile(file, path);
    }
    
    /**
     * 保存上传的文件
     *
     * @param file 文件
     * @param filePath 文件保存的文件相对路径
     * @return 文件保存的相对路径及访问地址
     */
    FilePath doSaveFile(MultipartFile file, String filePath);
    
    /**
     * 校验文件大小
     *
     * @param file    文件
     * @param maxSize 最大限制
     */
    default void checkSize(MultipartFile file, long maxSize) {
        if (maxSize < file.getSize()) {
            throw new BusinessException(ErrorCode.UPLOAD_TOO_BIG, maxSize / 1024);
        }
    }
    
    /**
     * 生成文件保存的相对路径
     *
     * @param file    文件
     * @param folder 文件保存的文件夹名称
     * @return 文件保存的相对路径 (格式: /resource/folder/yyyy/MM/dd/uuid.ext)
     */
    default String generateRelativePath(MultipartFile file, String folder) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new BusinessException(ErrorCode.FILE_NAME_NOT_FOUND);
        }
        fileName = IdUtil.fastSimpleUUID() + fileName.substring(fileName.lastIndexOf("."));
        LocalDate now = LocalDate.now();
        return File.separator + String.join(File.separator,
                CommonConstant.ROOT_FOLDER,
                folder,
                String.format("%04d", now.getYear()),
                String.format("%02d", now.getMonthValue()),
                String.format("%02d", now.getDayOfMonth()),
                fileName);
    }
}