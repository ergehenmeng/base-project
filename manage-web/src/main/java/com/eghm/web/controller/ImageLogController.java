package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.ImageLog;
import com.eghm.model.dto.ext.FilePath;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.image.ImageAddRequest;
import com.eghm.model.dto.image.ImageEditRequest;
import com.eghm.model.dto.image.ImageQueryRequest;
import com.eghm.service.common.FileService;
import com.eghm.service.common.ImageLogService;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author 二哥很猛
 * @date 2018/11/27 17:13
 */
@RestController
@Api(tags = "图片管理")
@AllArgsConstructor
@RequestMapping("/image")
public class ImageLogController {

    private final ImageLogService imageLogService;

    private final FileService fileService;

    /**
     * 分页查询图片列表
     *
     * @return 分页数据
     */
    @GetMapping("/listPage")
    @ApiOperation("图片列表(分页)")
    public Paging<ImageLog> listPage(ImageQueryRequest request) {
        Page<ImageLog> page = imageLogService.getByPage(request);
        return new Paging<>(page);
    }

    /**
     * 添加图片
     *
     * @return 成功
     */
    @PostMapping("/add")
    @Mark
    @ApiOperation("添加图片")
    @ApiImplicitParam(name = "imgFile", value = "文件流", required = true, paramType = "formData", dataType = "file")
    public RespBody<Void> addImage(@Valid ImageAddRequest request, @RequestParam("imgFile") MultipartFile imgFile) {
        if (imgFile != null && !imgFile.isEmpty()) {
            FilePath filePath = fileService.saveFile(imgFile);
            request.setPath(filePath.getPath());
            request.setSize(imgFile.getSize());
        }
        imageLogService.addImageLog(request);
        return RespBody.success();
    }

    /**
     * 编辑图片信息,不让重新上传因为图片可能已经被其他地方引用了
     *
     * @param request 更新参数
     * @return 成功
     */
    @PostMapping("/edit")
    @Mark
    @ApiOperation("添加图片")
    public RespBody<Void> editImage(@Valid ImageEditRequest request) {
        imageLogService.updateImageLog(request);
        return RespBody.success();
    }


    /**
     * 删除图片
     *
     * @param id 用户id
     * @return 删除
     */
    @PostMapping("/delete")
    @Mark
    @ApiOperation("删除图片")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public RespBody<Void> deleteImage(@RequestParam("id") Long id) {
        imageLogService.deleteImageLog(id);
        return RespBody.success();
    }

}
