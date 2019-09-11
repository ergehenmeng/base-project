package com.fanyin.controller.business;

import com.fanyin.dao.model.business.TaskLog;
import com.fanyin.model.dto.business.task.TaskLogQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.service.common.TaskLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public Paging<TaskLog> listPage(TaskLogQueryRequest request){
        PageInfo<TaskLog> byPage = taskLogService.getByPage(request);
        return new Paging<>(byPage);
    }

}
