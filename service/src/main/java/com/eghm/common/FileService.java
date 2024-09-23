package com.eghm.common;

import com.eghm.dto.ext.FilePath;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2019/11/15 14:49
 */
public interface FileService {

    /**
     * 保存文件
     *
     * @param file 文件
     * @return 文件保存的相对路径
     */
    FilePath saveFile(String key, @NotNull MultipartFile file);

    /**
     * 保存文件
     *
     * @param file   文件
     * @param folder 文件保存的文件夹名称 (主路径由全局定义,父级文件夹可在此处自定义)
     * @return 文件保存的相对路径
     */
    FilePath saveFile(String key, @NotNull MultipartFile file, String folder);

    /**
     * 保存文件
     *
     * @param file    文件
     * @param folder  文件保存的文件夹名称 (主路径由全局定义,父级文件夹可在此处自定义)
     * @param maxSize 文件最大限制 byte
     * @return 文件保存的相对路径
     */
    FilePath saveFile(String key, @NotNull MultipartFile file, String folder, long maxSize);

    /**
     * 保存文件
     *
     * @param file    文件
     * @param maxSize 文件最大限制 byte
     * @return 文件保存的相对路径
     */
    FilePath saveFile(String key, @NotNull MultipartFile file, long maxSize);

}
