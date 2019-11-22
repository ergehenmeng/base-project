package com.fanyin.controller.business;

import com.fanyin.annotation.Mark;
import com.fanyin.configuration.job.DynamicTask;
import com.fanyin.dao.model.business.TaskConfig;
import com.fanyin.model.dto.business.task.TaskEditRequest;
import com.fanyin.model.dto.business.task.TaskQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.common.TaskConfigService;
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

    @Autowired
    private TaskConfigService taskConfigService;

    @Autowired
    private DynamicTask dynamicTask;

    /**
     * 分页查询定时任务列表
     */
    @PostMapping("/business/task/list_page")
    @ResponseBody
    public Paging<TaskConfig> listPage(TaskQueryRequest request){
        PageInfo<TaskConfig> byPage = taskConfigService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 定时任务编辑页面
     */
    @GetMapping("/business/task/edit_page")
    public String editPage(Model model,Integer id){
        TaskConfig config = taskConfigService.getById(id);
        model.addAttribute("config",config);
        return "business/task/edit_page";
    }

    /**
     * 定时任务编辑保存
     */
    @PostMapping("/business/task/edit")
    @ResponseBody
    @Mark
    public RespBody edit(TaskEditRequest request){
        taskConfigService.editTaskConfig(request);
        return RespBody.getInstance();
    }

    /**
     * 刷新定时任务配置信息
     */
    @PostMapping("/business/task/refresh")
    @ResponseBody
    @Mark
    public RespBody refresh(){
        dynamicTask.openOrRefreshTask();
        return RespBody.getInstance();
    }
}
