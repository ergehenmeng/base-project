package com.eghm.web.controller;

import com.eghm.configuration.annotation.SkipLogger;
import com.eghm.model.dto.ext.FilePath;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.common.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传
 * 前端显示图片统一采用nginx方式
 * 建议使用第三方oss或则搭建HDFS
 * @author 二哥很猛
 * @date 2019/8/28 17:09
 */
@RestController
@Api(tags = "图片上传")
@AllArgsConstructor
@RequestMapping("/webapp/image")
public class ImageController {

    private final FileService fileService;

    @PostMapping("/upload")
    @ApiOperation("图片单张上传")
    @SkipLogger
    @ApiImplicitParam(name = "image", value = "文件流", paramType = "formData", dataType = "file", required = true)
    public RespBody<FilePath> image(@ApiParam(name = "image", value = "文件流") @RequestParam("image") MultipartFile image) {
        FilePath filePath = fileService.saveFile(image);
        return RespBody.success(filePath);
    }
}
