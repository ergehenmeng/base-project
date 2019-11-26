package com.fanyin.controller.business;

import com.fanyin.model.ext.FilePath;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.common.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 二哥很猛
 * @date 2019/11/26 15:33
 */
@RestController
public class UploadController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     */
    @PostMapping("/upload/image")
    public RespBody image(@RequestParam("image") MultipartFile image){
        FilePath filePath = fileService.saveFile(image);
        return RespBody.success(filePath);
    }
}
