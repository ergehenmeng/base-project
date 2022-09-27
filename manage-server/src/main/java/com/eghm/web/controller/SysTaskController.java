package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.task.config.SysTaskRegistrar;
import com.eghm.model.SysTask;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.task.TaskEditRequest;
import com.eghm.model.dto.task.TaskQueryRequest;
import com.eghm.service.common.SysTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2019/9/6 18:27
 */
@RestController
@Api(tags = "定时任务配置")
@AllArgsConstructor
@RequestMapping("/manage/task")
public class SysTaskController {

    private final SysTaskService sysTaskService;

    private final SysTaskRegistrar sysTaskRegistrar;

    @GetMapping("/listPage")
    @ApiOperation("定时任务列表(分页)")
    public PageData<SysTask> listPage(TaskQueryRequest request) {
        Page<SysTask> byPage = sysTaskService.getByPage(request);
        return PageData.toPage(byPage);
    }

    @PostMapping("/update")
    @ApiOperation("编辑定时任务")
    public RespBody<Void> update(@Validated @RequestBody TaskEditRequest request) {
        sysTaskService.update(request);
        return RespBody.success();
    }

    @PostMapping("/refresh")
    @ApiOperation("刷新定时任务")
    public RespBody<Void> refresh() {
        sysTaskRegistrar.loadOrRefreshTask();
        return RespBody.success();
    }
}
