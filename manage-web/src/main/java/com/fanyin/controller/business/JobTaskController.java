package com.fanyin.controller.business;

import com.fanyin.configuration.job.DynamicTask;
import com.fanyin.dao.model.business.JobTask;
import com.fanyin.model.dto.business.task.TaskQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.common.JobTaskService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 二哥很猛
 * @date 2019/9/6 18:27
 */
@Controller
public class JobTaskController {

    @Autowired
    private JobTaskService jobTaskService;

    @Autowired
    private DynamicTask dynamicTask;

    /**
     * 分页查询定时任务列表
     */
    @RequestMapping("/business/job_task/list_page")
    @ResponseBody
    public Paging<JobTask> listPage(TaskQueryRequest request){
        PageInfo<JobTask> byPage = jobTaskService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 刷新定时任务配置信息
     */
    @RequestMapping("/business/job_task/refresh")
    @ResponseBody
    public RespBody refresh(){
        dynamicTask.openRefreshTask();
        return RespBody.getInstance();
    }
}
