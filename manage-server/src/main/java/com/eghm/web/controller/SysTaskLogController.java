package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.task.TaskLogQueryRequest;
import com.eghm.model.SysTaskLog;
import com.eghm.service.common.SysTaskLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2019/9/11 15:37
 */
@RestController
@Api(tags = "定时任务日志")
@AllArgsConstructor
@RequestMapping(value = "/manage/task/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysTaskLogController {

    private final SysTaskLogService sysTaskLogService;

    @GetMapping("/listPage")
    @ApiOperation("日志列表(分页)")
    public RespBody<PageData<SysTaskLog>> listPage(TaskLogQueryRequest request) {
        Page<SysTaskLog> byPage = sysTaskLogService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/select")
    @ApiOperation("日志详情")
    public RespBody<String> select(@Validated IdDTO dto) {
        String errorMsg = sysTaskLogService.getErrorMsg(dto.getId());
        if (errorMsg == null) {
            return RespBody.success("暂无错误信息");
        }
        return RespBody.success(errorMsg);
    }
}
