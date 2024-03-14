package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.log.WebappQueryRequest;
import com.eghm.service.sys.WebappLogService;
import com.eghm.vo.log.WebappLogResponse;
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
 * @since 2023/7/24
 */
@RestController
@Api(tags = "会员操作日志")
@AllArgsConstructor
@RequestMapping(value = "/manage/webapp/log", produces = MediaType.APPLICATION_JSON_VALUE)
public class WebappLogController {

    private final WebappLogService webappLogService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<WebappLogResponse>> listPage(@Validated WebappQueryRequest request) {
        Page<WebappLogResponse> page = webappLogService.getByPage(request);
        return RespBody.success(PageData.toPage(page));
    }
}
