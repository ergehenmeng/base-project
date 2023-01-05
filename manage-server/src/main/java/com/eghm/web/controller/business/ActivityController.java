package com.eghm.web.controller.business;

import com.eghm.model.Activity;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.activity.ActivityAddRequest;
import com.eghm.model.dto.business.activity.ActivityConfigRequest;
import com.eghm.model.dto.business.activity.ActivityDeleteRequest;
import com.eghm.model.dto.business.activity.ActivityEditRequest;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.business.activity.ActivityBaseResponse;
import com.eghm.service.business.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiImplicitParams({
        @ApiImplicitParam(name = "month", value = "月份yyyy-MM", required = true),
        @ApiImplicitParam(name = "scenicId", value = "景区id")
    })
    @ApiOperation("查询月活动")
    public List<ActivityBaseResponse> select(@RequestParam("month") String month,
                                             @RequestParam(value = "scenicId", required = false) Long scenicId) {
        return activityService.getMonthActivity(month, scenicId);
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
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public Activity select(@RequestParam("id") Long id) {
        return activityService.selectById(id);
    }

    @PostMapping("/delete")
    @ApiOperation("删除活动")
    public RespBody<Void> open(@RequestBody @Validated ActivityDeleteRequest request) {
        activityService.delete(request);
        return RespBody.success();
    }

}
