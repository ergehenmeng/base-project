package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.TaskLog;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.task.TaskLogQueryRequest;
import com.eghm.service.common.TaskLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2019/9/11 15:37
 */
@RestController
@Api(tags = "定时任务日志")
@AllArgsConstructor
@RequestMapping("/taskLog")
public class TaskLogController {

    private final TaskLogService taskLogService;

    @GetMapping("/listPage")
    @ApiOperation("日志列表(分页)")
    public RespBody<PageData<TaskLog>> listPage(TaskLogQueryRequest request) {
        Page<TaskLog> byPage = taskLogService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/select")
    @ApiOperation("错误日志查询")
    @ApiImplicitParam(name = "id", value = "id",  required = true)
    public RespBody<String> select(@RequestParam("id") Long id) {
        String errorMsg = taskLogService.getErrorMsg(id);
        return RespBody.success(errorMsg);
    }
}
