package com.eghm.web.controller;

import com.eghm.configuration.task.config.SystemTaskRegistrar;
import com.eghm.dao.model.TaskConfig;
import com.eghm.model.dto.task.TaskEditRequest;
import com.eghm.model.dto.task.TaskQueryRequest;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.common.TaskConfigService;
import com.eghm.web.annotation.Mark;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 二哥很猛
 * @date 2019/9/6 18:27
 */
@Controller
public class TaskConfigController {

    private TaskConfigService taskConfigService;

    private SystemTaskRegistrar systemTaskRegistrar;

    @Autowired
    public void setTaskConfigService(TaskConfigService taskConfigService) {
        this.taskConfigService = taskConfigService;
    }

    @Autowired
    public void setSystemTaskRegistrar(SystemTaskRegistrar systemTaskRegistrar) {
        this.systemTaskRegistrar = systemTaskRegistrar;
    }

    /**
     * 分页查询定时任务列表
     */
    @PostMapping("/task/list_page")
    @ResponseBody
    public Paging<TaskConfig> listPage(TaskQueryRequest request) {
        PageInfo<TaskConfig> byPage = taskConfigService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 定时任务编辑页面
     */
    @GetMapping("/task/edit_page")
    public String editPage(Model model, Integer id) {
        TaskConfig config = taskConfigService.getById(id);
        model.addAttribute("config", config);
        return "task/edit_page";
    }

    /**
     * 定时任务编辑保存
     */
    @PostMapping("/task/edit")
    @ResponseBody
    @Mark
    public RespBody<Object> edit(TaskEditRequest request) {
        taskConfigService.editTaskConfig(request);
        return RespBody.success();
    }

    /**
     * 刷新定时任务配置信息
     */
    @PostMapping("/task/refresh")
    @ResponseBody
    @Mark
    public RespBody<Object> refresh() {
        systemTaskRegistrar.loadOrRefreshTask();
        return RespBody.success();
    }
}
