package com.eghm.web.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sys.image.ImageAddRequest;
import com.eghm.dto.sys.image.ImageEditRequest;
import com.eghm.dto.sys.image.ImageQueryRequest;
import com.eghm.service.common.ImageLogService;
import com.eghm.vo.log.ImageLogResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2018/11/27 17:13
 */
@RestController
@Tag(name="图片管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/image", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageLogController {

    private final ImageLogService imageLogService;

    @GetMapping("/listPage")
    @Operation(summary = "图片列表(分页)")
    public RespBody<PageData<ImageLogResponse>> listPage(ImageQueryRequest request) {
        Page<ImageLogResponse> page = imageLogService.getByPage(request);
        return RespBody.success(PageData.toPage(page));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody ImageAddRequest request) {
        imageLogService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody ImageEditRequest request) {
        imageLogService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO dto) {
        imageLogService.delete(dto.getId());
        return RespBody.success();
    }

}
