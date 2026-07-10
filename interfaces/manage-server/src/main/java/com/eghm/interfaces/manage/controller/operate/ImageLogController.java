package com.eghm.interfaces.manage.controller.operate;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.IdDTO;
import com.eghm.application.shared.dto.ext.PageData;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.operate.image.ImageAddRequest;
import com.eghm.application.shared.dto.operate.image.ImageEditRequest;
import com.eghm.application.shared.dto.operate.image.ImageQueryRequest;
import com.eghm.application.operate.query.ImageLogQueryService;
import com.eghm.application.operate.service.ImageLogApplicationService;
import com.eghm.application.shared.vo.operate.log.ImageLogResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2018/11/27 17:13
 */
@RestController
@AllArgsConstructor
@Tag(name = "图片管理")
@RequestMapping(value = "/manage/image", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageLogController {

    private final ImageLogApplicationService imageLogService;

    private final ImageLogQueryService imageLogQueryService;

    @GetMapping("/listPage")
    @Operation(summary = "图片列表(分页)")
    public RespBody<PageData<ImageLogResponse>> listPage(@ParameterObject ImageQueryRequest request) {
        Page<ImageLogResponse> page = imageLogQueryService.getByPage(request.createPage(), request);
        return RespBody.success(PageData.convert(page));
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
