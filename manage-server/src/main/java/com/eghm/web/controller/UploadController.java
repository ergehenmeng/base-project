package com.eghm.web.controller;


import com.eghm.annotation.SkipPerm;
import com.eghm.common.FileService;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.ext.FilePath;
import com.eghm.dto.ext.RespBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 二哥很猛
 * @since 2019/11/26 15:33
 */
@RestController
@Tag(name="文件上传")
@AllArgsConstructor
@RequestMapping(value = "/manage/file", produces = MediaType.APPLICATION_JSON_VALUE)
public class UploadController {

    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Parameter(name = "file", description = "file流", required = true, schema = @Schema(type = "string", format = "binary"))
    @Operation(summary = "单文件上传")
    @SkipPerm
    public RespBody<FilePath> upload(@RequestParam("file") MultipartFile file) {
        FilePath filePath = fileService.saveFile(CommonConstant.MANAGE + SecurityHolder.getUserId(), file);
        return RespBody.success(filePath);
    }
}
