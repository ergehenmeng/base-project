package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.line.LineQueryDTO;
import com.eghm.vo.business.line.LineListVO;
import com.eghm.vo.business.line.LineVO;
import com.eghm.vo.business.line.config.LineConfigVO;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/26
 */
@RestController
@Api(tags = "线路")
@AllArgsConstructor
@RequestMapping("/webapp/line")
public class LineController {

    private final LineService lineService;

    private final LineConfigService lineConfigService;

    @GetMapping("/listPage")
    @ApiOperation("线路列表")
    public List<LineListVO> getByPage(LineQueryDTO dto) {
        return lineService.getByPage(dto);
    }

    @GetMapping("/detail")
    @ApiOperation("线路详情")
    public LineVO detail(@Validated IdDTO request) {
        return lineService.detailById(request.getId());
    }

    @GetMapping("/price/list")
    @ApiOperation("线路日价格")
    @ApiImplicitParam(name = "lineId", value = "线路id", required = true)
    public List<LineConfigVO> getPriceList(@NotNull(message = "线路id不能为空") @RequestParam("lineId") Long lineId) {
        return lineConfigService.getPriceList(lineId);
    }
}
