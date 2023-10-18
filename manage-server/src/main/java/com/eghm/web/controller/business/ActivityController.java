package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.activity.*;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.Activity;
import com.eghm.service.business.ActivityService;
import com.eghm.vo.business.activity.ActivityBaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
@RestController
@Api(tags = "活动管理")
@AllArgsConstructor
@RequestMapping("/manage/activity")
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/month")
    @ApiOperation("查询月活动")
    public RespBody<List<ActivityBaseResponse>> month(@Validated ActivityQueryRequest request) {
        List<ActivityBaseResponse> monthActivity = activityService.getMonthActivity(request);
        return RespBody.success(monthActivity);
    }

    @PostMapping("/config")
    @ApiOperation("配置活动")
    public RespBody<Void> config(@RequestBody @Validated ActivityConfigRequest request) {
        activityService.create(request);
        return RespBody.success();
    }

    @PostMapping("/create")
    @ApiOperation("创建活动")
    public RespBody<Void> create(@RequestBody @Validated ActivityAddRequest request) {
        activityService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新活动")
    public RespBody<Void> update(@RequestBody @Validated ActivityEditRequest request) {
        activityService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询活动")
    public RespBody<Activity> select(@Validated IdDTO dto) {
        Activity activity = activityService.selectById(dto.getId());
        return RespBody.success(activity);
    }

    @PostMapping("/delete")
    @ApiOperation("删除活动")
    public RespBody<Void> delete(@RequestBody @Validated ActivityDeleteRequest request) {
        activityService.delete(request);
        return RespBody.success();
    }

}
