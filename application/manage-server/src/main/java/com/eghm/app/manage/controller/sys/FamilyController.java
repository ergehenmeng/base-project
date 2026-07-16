package com.eghm.app.manage.controller.sys;

import com.eghm.foundation.core.dto.IdRequest;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.platform.config.dto.FamilyAddRequest;
import com.eghm.platform.config.dto.FamilyEditRequest;
import com.eghm.platform.config.service.FamilyService;
import com.eghm.platform.config.vo.FamilyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2025/12/17
 */
@RestController
@AllArgsConstructor
@Tag(name = "族谱管理")
@RequestMapping(value = "/manage/family", produces = MediaType.APPLICATION_JSON_VALUE)
public class FamilyController {

    private final FamilyService familyService;

    @GetMapping(value = "/list")
    @Operation(summary = "列表")
    public RespBody<FamilyResponse> list() {
        FamilyResponse responseList = familyService.getList();
        return RespBody.success(responseList);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<String> create(@Validated @RequestBody FamilyAddRequest request) {
        String id = familyService.create(request);
        return RespBody.success(id);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody FamilyEditRequest request) {
        familyService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@Validated @RequestBody IdRequest request) {
        familyService.delete(request.getId());
        return RespBody.success();
    }

}
