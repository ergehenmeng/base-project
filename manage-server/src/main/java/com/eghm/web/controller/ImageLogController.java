package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.image.ImageAddRequest;
import com.eghm.dto.image.ImageEditRequest;
import com.eghm.dto.image.ImageQueryRequest;
import com.eghm.model.ImageLog;
import com.eghm.service.common.ImageLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2018/11/27 17:13
 */
@RestController
@Api(tags = "图片管理")
@AllArgsConstructor
@RequestMapping("/manage/image")
public class ImageLogController {

    private final ImageLogService imageLogService;

    @GetMapping("/listPage")
    @ApiOperation("图片列表(分页)")
    public RespBody<PageData<ImageLog>> listPage(ImageQueryRequest request) {
        Page<ImageLog> page = imageLogService.getByPage(request);
        return RespBody.success(PageData.toPage(page));
    }

    @PostMapping("/create")
    @ApiOperation("添加图片")
    public RespBody<Void> create(@Validated @RequestBody ImageAddRequest request) {
        imageLogService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("添加图片")
    public RespBody<Void> update(@Validated @RequestBody ImageEditRequest request) {
        imageLogService.update(request);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除图片")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        imageLogService.delete(dto.getId());
        return RespBody.success();
    }

}
