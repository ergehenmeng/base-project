package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.line.LineQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineService;
import com.eghm.vo.business.line.LineDetailVO;
import com.eghm.vo.business.line.LineVO;
import com.eghm.vo.business.line.config.LineConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/26
 */
@RestController
@Api(tags = "线路")
@AllArgsConstructor
@RequestMapping(value = "/webapp/line", produces = MediaType.APPLICATION_JSON_VALUE)
public class LineController {

    private final LineService lineService;

    private final LineConfigService lineConfigService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<List<LineVO>> getByPage(LineQueryDTO dto) {
        List<LineVO> byPage = lineService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public RespBody<LineDetailVO> detail(@Validated IdDTO request) {
        LineDetailVO detail = lineService.detailById(request.getId());
        return RespBody.success(detail);
    }

    @GetMapping("/price/list")
    @ApiOperation("线路日价格")
    public RespBody<List<LineConfigVO>> getPriceList(@Validated IdDTO dto) {
        List<LineConfigVO> voList = lineConfigService.getPriceList(dto.getId());
        return RespBody.success(voList);
    }
}
