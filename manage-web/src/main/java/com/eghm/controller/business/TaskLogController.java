package com.eghm.controller.business;

import com.eghm.dao.model.business.TaskLog;
import com.eghm.model.dto.business.task.TaskLogQueryRequest;
import com.eghm.model.ext.Paging;
import com.eghm.service.common.TaskLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 二哥很猛
 * @date 2019/9/11 15:37
 */
@Controller
public class TaskLogController {

    @Autowired
    private TaskLogService taskLogService;

    /**
     * 分页查询定时任务列表
     */
    @PostMapping("/business/task_log/list_page")
    @ResponseBody
    public Paging<TaskLog> listPage(TaskLogQueryRequest request) {
        PageInfo<TaskLog> byPage = taskLogService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 错误信息
     */
    @GetMapping("/business/task_log/error_msg")
    public String errorMsg(Model model, Integer id) {
        String errorMsg = taskLogService.getErrorMsg(id).getErrorMsg();
        model.addAttribute("response", errorMsg);
        return "query_page";
    }
}
