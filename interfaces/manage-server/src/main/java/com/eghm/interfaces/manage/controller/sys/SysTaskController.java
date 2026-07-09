package com.eghm.interfaces.manage.controller.sys;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.schedule.config.TaskRegistrar;
import com.eghm.application.shared.dto.ext.PageData;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.sys.task.TaskEditRequest;
import com.eghm.application.shared.dto.sys.task.TaskQueryRequest;
import com.eghm.application.shared.dto.sys.task.TaskRunRequest;
import com.eghm.application.system.service.SysTaskApplicationService;
import com.eghm.application.shared.vo.operate.task.SysTaskResponse;
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
@AllArgsConstructor
@Tag(name = "定时任务配置")
@RequestMapping(value = "/manage/task", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysTaskController {

    private final SysTaskApplicationService sysTaskService;

    private final TaskRegistrar taskRegistrar;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<SysTaskResponse>> listPage(@ParameterObject TaskQueryRequest request) {
        Page<SysTaskResponse> byPage = sysTaskService.getByPage(request);
        return RespBody.success(PageData.convert(byPage));
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
