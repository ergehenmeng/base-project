package com.eghm.controller;

import com.eghm.model.ext.FilePath;
import com.eghm.model.ext.RespBody;
import com.eghm.service.common.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传
 *
 * @author 二哥很猛
 * @date 2019/8/28 17:09
 */
@RestController
@Api(tags = "图片上传")
public class ImageController extends AbstractController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     */
    @PostMapping("/upload/image")
    @ApiParam(name = "image", value = "文件流", required = true)
    @ApiOperation("图片单张上传")
    public RespBody image(@RequestParam("image") MultipartFile image) {
        FilePath filePath = fileService.saveFile(image);
        return RespBody.success(filePath);
    }
}
