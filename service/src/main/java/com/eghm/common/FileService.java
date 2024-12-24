package com.eghm.common;

import com.eghm.dto.ext.FilePath;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

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
     * @param file 文件
     * @param maxSize 最大限制
     */
    default void checkSize(MultipartFile file, long maxSize) {
        if (maxSize < file.getSize()) {
            throw new BusinessException(ErrorCode.UPLOAD_TOO_BIG, maxSize / 1024);
        }
    }
}
