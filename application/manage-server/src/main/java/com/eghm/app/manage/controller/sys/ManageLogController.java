package com.eghm.app.manage.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.dto.ext.PageData;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.platform.audit.dto.ManageQueryRequest;
import com.eghm.platform.audit.service.ManageLogService;
import com.eghm.platform.audit.vo.ManageLogResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2019/1/16 10:37
 */
@RestController
@AllArgsConstructor
@Tag(name = "操作日志管理")
@RequestMapping(value = "/manage/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageLogController {

    private final ManageLogService manageLogService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<ManageLogResponse>> listPage(@ParameterObject ManageQueryRequest request) {
        Page<ManageLogResponse> byPage = manageLogService.getByPage(request);
        return RespBody.success(PageData.convert(byPage));
    }

}
