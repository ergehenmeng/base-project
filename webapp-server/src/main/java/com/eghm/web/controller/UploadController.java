package com.eghm.web.controller;

import com.eghm.common.FileService;
import com.eghm.annotation.SkipLogger;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.FilePath;
import com.eghm.dto.ext.RespBody;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传
 * 前端显示图片统一采用nginx方式
 * 建议使用第三方oss或则搭建HDFS
 *
 * @author 二哥很猛
 * @since 2019/8/28 17:09
 */
@RestController
@Api(tags = "图片上传")
@AllArgsConstructor
@RequestMapping(value = "/webapp/file", produces = MediaType.APPLICATION_JSON_VALUE)
public class UploadController {

    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParam(name = "file", value = "file流", paramType = "formData", dataType = "file", required = true)
    @ApiOperation("单文件上传")
    @SkipLogger
    @AccessToken
    public RespBody<FilePath> upload(@RequestParam("file") MultipartFile file) {
        FilePath filePath = fileService.saveFile(CommonConstant.WEBAPP + ApiHolder.getMemberId(), file);
        return RespBody.success(filePath);
    }
}
