package com.eghm.web.controller;


import com.eghm.model.dto.ext.FilePath;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.common.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "文件上传")
public class UploadController {

    private FileService fileService;

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload/image")
    @ApiImplicitParam(name = "image", value = "image", paramType = "formData", dataType = "file", required = true)
    @ApiOperation("图片上传")
    public RespBody<Object> image(@RequestParam("image") MultipartFile image) {
        FilePath filePath = fileService.saveFile(image);
        return RespBody.success(filePath);
    }
}
