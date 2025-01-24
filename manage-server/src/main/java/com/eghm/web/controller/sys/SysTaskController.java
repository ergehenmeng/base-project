package com.eghm.web.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.task.config.TaskRegistrar;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sys.task.TaskEditRequest;
import com.eghm.dto.sys.task.TaskQueryRequest;
import com.eghm.dto.sys.task.TaskRunRequest;
import com.eghm.service.sys.SysTaskService;
import com.eghm.vo.operate.task.SysTaskResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2019/9/6 18:27
 */
@RestController
@Tag(name = "定时任务配置")
@AllArgsConstructor
@RequestMapping(value = "/manage/task", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysTaskController {

    private final SysTaskService sysTaskService;

    private final TaskRegistrar taskRegistrar;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<SysTaskResponse>> listPage(@ParameterObject TaskQueryRequest request) {
        Page<SysTaskResponse> byPage = sysTaskService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody TaskEditRequest request) {
        sysTaskService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/refresh")
    @Operation(summary = "刷新")
    public RespBody<Void> refresh() {
        taskRegistrar.reloadTask();
        return RespBody.success();
    }

    @PostMapping(value = "/execute", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "执行")
    public RespBody<Void> execute(@Validated @RequestBody TaskRunRequest request) {
        sysTaskService.execute(request.getId(), request.getArgs());
        return RespBody.success();
    }
}
