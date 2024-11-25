package com.eghm.common.impl;

import com.eghm.common.FileService;
import com.eghm.dto.ext.FilePath;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 二哥很猛
 * @since 2024/11/22
 */

@Slf4j
@AllArgsConstructor
public class AliOssFileServiceImpl implements FileService {

    @Override
    public FilePath saveFile(String key, MultipartFile file) {
        return null;
    }

    @Override
    public FilePath saveFile(String key, MultipartFile file, String folder) {
        return null;
    }

    @Override
    public FilePath saveFile(String key, MultipartFile file, String folder, long maxSize) {
        return null;
    }

    @Override
    public FilePath saveFile(String key, MultipartFile file, long maxSize) {
        return null;
    }
}
