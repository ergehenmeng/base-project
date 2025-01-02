package com.eghm.web.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.sys.task.TaskLogQueryRequest;
import com.eghm.service.sys.SysTaskLogService;
import com.eghm.vo.operate.log.SysTaskLogResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author 二哥很猛
 * @since 2019/9/11 15:37
 */
@RestController
@Tag(name= "定时任务日志")
@AllArgsConstructor
@RequestMapping(value = "/manage/task/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysTaskLogController {

    private final SysTaskLogService sysTaskLogService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<SysTaskLogResponse>> listPage(TaskLogQueryRequest request) {
        Page<SysTaskLogResponse> byPage = sysTaskLogService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<String> select(@Validated IdDTO dto) {
        String errorMsg = sysTaskLogService.getErrorMsg(dto.getId());
        return RespBody.success(Objects.requireNonNullElse(errorMsg, "暂无错误信息"));
    }
}
