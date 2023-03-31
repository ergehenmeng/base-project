package com.eghm.web.controller;


import com.eghm.dto.ext.FilePath;
import com.eghm.service.common.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 二哥很猛
 * @date 2019/11/26 15:33
 */
@RestController
@Api(tags = "文件上传")
@AllArgsConstructor
@RequestMapping("/manage/file")
public class UploadController {

    private final FileService fileService;

    @PostMapping("/upload")
    @ApiImplicitParam(name = "file", value = "file流", paramType = "formData", dataType = "file", required = true)
    @ApiOperation("单文件上传")
    public FilePath file(@RequestParam("file") MultipartFile file) {
        return fileService.saveFile(file);
    }
}
