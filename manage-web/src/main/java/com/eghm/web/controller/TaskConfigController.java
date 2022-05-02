package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.task.config.SystemTaskRegistrar;
import com.eghm.dao.model.TaskConfig;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.task.TaskEditRequest;
import com.eghm.model.dto.task.TaskQueryRequest;
import com.eghm.service.common.TaskConfigService;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 二哥很猛
 * @date 2019/9/6 18:27
 */
@RestController
@Api(tags = "定时任务配置")
@AllArgsConstructor
public class TaskConfigController {

    private final TaskConfigService taskConfigService;

    private final SystemTaskRegistrar systemTaskRegistrar;

    /**
     * 分页查询定时任务列表
     */
    @GetMapping("/task/list_page")
    @ApiOperation("定时任务列表(分页)")
    public Paging<TaskConfig> listPage(TaskQueryRequest request) {
        Page<TaskConfig> byPage = taskConfigService.getByPage(request);
        return new Paging<>(byPage);
    }


    /**
     * 定时任务编辑保存
     */
    @PostMapping("/task/edit")
    @Mark
    @ApiOperation("编辑定时任务")
    public RespBody<Object> edit(@Valid TaskEditRequest request) {
        taskConfigService.editTaskConfig(request);
        return RespBody.success();
    }

    /**
     * 刷新定时任务配置信息
     */
    @PostMapping("/task/refresh")
    @Mark
    @ApiOperation("刷新定时任务")
    public RespBody<Object> refresh() {
        systemTaskRegistrar.loadOrRefreshTask();
        return RespBody.success();
    }
}
