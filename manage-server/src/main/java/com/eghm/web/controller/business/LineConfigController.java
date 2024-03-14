package com.eghm.web.controller.business;

import com.eghm.dto.business.line.config.LineConfigOneRequest;
import com.eghm.dto.business.line.config.LineConfigQueryRequest;
import com.eghm.dto.business.line.config.LineConfigRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineService;
import com.eghm.vo.business.line.config.LineConfigResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/12/27
 */
@RestController
@Api(tags = "线路配置")
@AllArgsConstructor
@RequestMapping(value = "/manage/line/config", produces = MediaType.APPLICATION_JSON_VALUE)
public class LineConfigController {

    private final LineService lineService;

    private final LineConfigService lineConfigService;

    @GetMapping("/month")
    @ApiOperation("线路月度价格")
    public RespBody<List<LineConfigResponse>> month(@Validated LineConfigQueryRequest request) {
        lineService.selectByIdRequired(request.getLineId());
        List<LineConfigResponse> monthList = lineConfigService.getMonthList(request);
        return RespBody.success(monthList);
    }

    @PostMapping(value = "/setup", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("批量设置线路价格")
    public RespBody<Void> setup(@Validated @RequestBody LineConfigRequest request) {
        lineConfigService.setup(request);
        return RespBody.success();
    }

    @PostMapping(value = "/setDay", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("设置某一天价格")
    public RespBody<Void> setDay(@Validated @RequestBody LineConfigOneRequest request) {
        lineConfigService.setDay(request);
        return RespBody.success();
    }
}
