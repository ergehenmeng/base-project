package com.eghm.interfaces.manage.controller.sys;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.ext.PageData;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.sys.log.WebappQueryRequest;
import com.eghm.application.system.query.WebappLogQueryService;
import com.eghm.application.shared.vo.operate.log.WebappLogResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2023/7/24
 */
@RestController
@AllArgsConstructor
@Tag(name = "会员操作日志")
@RequestMapping(value = "/manage/webapp/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class WebappLogController {

    private final WebappLogQueryService webappLogQueryService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<WebappLogResponse>> listPage(@ParameterObject @Validated WebappQueryRequest request) {
        Page<WebappLogResponse> page = webappLogQueryService.getByPage(request.createPage(), request);
        return RespBody.success(PageData.convert(page));
    }
}
