package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.SysTaskLog;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.task.TaskLogQueryRequest;
import com.eghm.service.common.SysTaskLogService;
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
@RequestMapping("/manage/task/log")
public class SysTaskLogController {

    private final SysTaskLogService sysTaskLogService;

    @GetMapping("/listPage")
    @ApiOperation("日志列表(分页)")
    public PageData<SysTaskLog> listPage(TaskLogQueryRequest request) {
        Page<SysTaskLog> byPage = sysTaskLogService.getByPage(request);
        return PageData.toPage(byPage);
    }

    @GetMapping("/select")
    @ApiOperation("日志详情")
    @ApiImplicitParam(name = "id", value = "id",  required = true)
    public String select(@RequestParam("id") Long id) {
        return sysTaskLogService.getErrorMsg(id);
    }
}
