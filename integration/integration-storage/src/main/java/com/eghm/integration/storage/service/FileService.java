package com.eghm.integration.storage.service;

import cn.hutool.core.util.IdUtil;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.integration.storage.dto.FilePath;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2019/11/15 14:49
 */

public interface FileService {

    /**
     * 保存文件
     *
     * @param key  用户key
     * @param file 文件
     * @return 文件保存的相对路径
     */
    FilePath saveFile(String key, MultipartFile file);

    /**
     * 保存文件
     *
     * @param key  key
     * @param file 文件
     * @param folder 文件保存的文件夹名称
     * @return 文件保存的相对路径
     */
    FilePath saveFile(String key, MultipartFile file, String folder);

    /**
     * 保存文件
     *
     * @param key     用户key
     * @param file    文件
     * @param folder  文件保存的文件夹名称 (主路径由全局定义,父级文件夹可在此处自定义)
     * @param maxSize 文件最大限制 byte
     * @return 文件保存的相对路径
     */
    FilePath saveFile(String key, MultipartFile file, String folder, long maxSize);

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
        return CommonConstant.ROOT_FOLDER + folder + File.separator + now.getYear() + File.separator + now.getMonthValue() + File.separator + now.getDayOfMonth() + File.separator + fileName;
    }
}
