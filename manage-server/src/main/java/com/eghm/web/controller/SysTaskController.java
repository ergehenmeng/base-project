package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.task.config.SysTaskRegistrar;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.task.TaskEditRequest;
import com.eghm.dto.task.TaskQueryRequest;
import com.eghm.dto.task.TaskRunRequest;
import com.eghm.model.SysTask;
import com.eghm.service.common.SysTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2019/9/6 18:27
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
    public RespBody<PageData<SysTask>> listPage(TaskQueryRequest request) {
        Page<SysTask> byPage = sysTaskService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑定时任务")
    public RespBody<Void> update(@Validated @RequestBody TaskEditRequest request) {
        sysTaskService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("刷新定时任务")
    public RespBody<Void> refresh() {
        sysTaskRegistrar.reloadTask();
        return RespBody.success();
    }

    @PostMapping(value = "/execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("执行一次任务")
    public RespBody<Void> execute(@Validated @RequestBody TaskRunRequest request) {
        sysTaskService.execute(request.getId(), request.getArgs());
        return RespBody.success();
    }
}
