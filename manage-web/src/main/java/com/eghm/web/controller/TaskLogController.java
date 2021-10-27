package com.eghm.web.controller;

import com.eghm.dao.model.TaskLog;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.task.TaskLogQueryRequest;
import com.eghm.service.common.TaskLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2019/9/11 15:37
 */
@RestController
public class TaskLogController {

    private TaskLogService taskLogService;

    @Autowired
    public void setTaskLogService(TaskLogService taskLogService) {
        this.taskLogService = taskLogService;
    }

    /**
     * 分页查询定时任务列表
     */
    @GetMapping("/task_log/list_page")
    public Paging<TaskLog> listPage(TaskLogQueryRequest request) {
        PageInfo<TaskLog> byPage = taskLogService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 错误信息
     */
    @GetMapping("/task_log/error_msg")
    public String errorMsg(Model model, Long id) {
        String errorMsg = taskLogService.getErrorMsg(id).getErrorMsg();
        model.addAttribute("response", errorMsg);
        return "query_page";
    }
}
