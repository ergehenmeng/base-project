package com.eghm.web.controller;


import com.eghm.configuration.annotation.SkipPerm;
import com.eghm.dto.ext.FilePath;
import com.eghm.dto.ext.RespBody;
import com.eghm.common.FileService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/11/26 15:33
 */
@RestController
@Api(tags = "文件上传")
@AllArgsConstructor
@RequestMapping(value = "/manage/file", produces = MediaType.APPLICATION_JSON_VALUE)
public class UploadController {

    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParam(name = "file", value = "file流", paramType = "formData", dataType = "file", required = true)
    @ApiOperation("单文件上传")
    @SkipPerm
    public RespBody<FilePath> upload(@RequestParam("file") MultipartFile file) {
        FilePath filePath = fileService.saveFile(file);
        return RespBody.success(filePath);
    }

    @PostMapping(value = "/multipleUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParam(name = "files", value = "file流", paramType = "formData", dataType = "file", required = true)
    @ApiOperation("多文件上传")
    @SkipPerm
    public RespBody<List<FilePath>> upload(@RequestParam("files") MultipartFile[] files) {
        List<FilePath> pathList = new ArrayList<>(16);
        for (MultipartFile file : files) {
            FilePath filePath = fileService.saveFile(file);
            pathList.add(filePath);
        }
        return RespBody.success(pathList);
    }
}
