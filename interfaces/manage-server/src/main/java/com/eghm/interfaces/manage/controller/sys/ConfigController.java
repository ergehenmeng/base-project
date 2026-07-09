package com.eghm.interfaces.manage.controller.sys;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PageData;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.sys.config.ConfigEditRequest;
import com.eghm.application.shared.dto.sys.config.ConfigQueryRequest;
import com.eghm.application.system.port.in.SysConfigService;
import com.eghm.application.shared.vo.sys.ext.SysConfigResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author 二哥很猛
 * @since 2018/1/12 17:40
 */
@RestController
@AllArgsConstructor
@Tag(name = "系统参数管理")
@RequestMapping(value = "/manage/config", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConfigController {

    private final SysConfigService sysConfigService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<SysConfigResponse>> listPage(@ParameterObject ConfigQueryRequest request) {
        Page<SysConfigResponse> listByPage = sysConfigService.getByPage(request);
        return RespBody.success(PageData.convert(listByPage));
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody ConfigEditRequest request) {
        sysConfigService.update(request);
        return RespBody.success();
    }

}
