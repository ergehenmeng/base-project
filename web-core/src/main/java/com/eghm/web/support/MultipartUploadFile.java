package com.eghm.web.support;

import com.eghm.dto.ext.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Spring上传文件适配器.
 *
 * @author 二哥很猛
 */
@RequiredArgsConstructor
public class MultipartUploadFile implements UploadFile {

    private final MultipartFile file;

    @Override
    public String getOriginalFilename() {
        return file.getOriginalFilename();
    }

    @Override
    public long getSize() {
        return file.getSize();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return file.getInputStream();
    }
}
