package com.eghm.dto.ext;

import java.io.IOException;
import java.io.InputStream;

/**
 * 上传文件.
 *
 * @author 二哥很猛
 */
public interface UploadFile {

    /**
     * 原始文件名.
     *
     * @return 原始文件名
     */
    String getOriginalFilename();

    /**
     * 文件大小.
     *
     * @return 文件大小
     */
    long getSize();

    /**
     * 打开文件输入流.
     *
     * @return 输入流
     * @throws IOException 文件读取异常
     */
    InputStream getInputStream() throws IOException;
}
