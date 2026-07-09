package com.eghm.utils;

import cn.hutool.core.io.FileTypeUtil;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 二哥很猛
 * @since 2025/9/18
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtil {

    /**
     * 校验文件类型
     *
     * @param file  文件
     * @param types 文件类型
     */
    public static void checkFileType(MultipartFile file, String... types) {
        try {
            String fileType = FileTypeUtil.getType(file.getInputStream());
            for (String type : types) {
                if (fileType.equals(type)) {
                    return;
                }
            }
            String supported = String.join(",", types);
            throw new BusinessException(ErrorCode.FILE_TYPE_SUPPORT, supported);
        } catch (IOException e) {
            log.warn("文件类型校验异常", e);
            throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
        }
    }
}
