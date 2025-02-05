package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.activity.*;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ActivityService;
import com.eghm.vo.business.activity.ActivityDetailResponse;
import com.eghm.vo.business.activity.ActivityResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@RestController
@Tag(name="活动管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/activity", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/month")
    @Operation(summary = "查询月活动")
    public RespBody<List<ActivityResponse>> month(@ParameterObject @Validated ActivityQueryRequest request) {
        List<ActivityResponse> monthActivity = activityService.getMonthActivity(request);
        return RespBody.success(monthActivity);
    }

    @PostMapping(value = "/config", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "配置活动")
    public RespBody<Void> config(@RequestBody @Validated ActivityConfigRequest request) {
        activityService.createBatch(request);
        return RespBody.success();
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@RequestBody @Validated ActivityAddRequest request) {
        activityService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@RequestBody @Validated ActivityEditRequest request) {
        activityService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<ActivityDetailResponse> select(@Validated IdDTO dto) {
        ActivityDetailResponse response = activityService.getByDetail(dto.getId());
        return RespBody.success(response);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated ActivityDeleteRequest request) {
        activityService.delete(request);
        return RespBody.success();
    }

}
