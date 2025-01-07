package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.line.LineQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.VisitType;
import com.eghm.service.business.LineConfigService;
import com.eghm.service.business.LineService;
import com.eghm.vo.business.line.LineDetailVO;
import com.eghm.vo.business.line.LineVO;
import com.eghm.vo.business.line.config.LineConfigVO;
import com.eghm.web.annotation.VisitRecord;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name="线路")
@AllArgsConstructor
@RequestMapping(value = "/webapp/line", produces = MediaType.APPLICATION_JSON_VALUE)
public class LineController {

    private final LineService lineService;

    private final LineConfigService lineConfigService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    @VisitRecord(VisitType.PRODUCT_LIST)
    public RespBody<List<LineVO>> getByPage(LineQueryDTO dto) {
        List<LineVO> byPage = lineService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    @VisitRecord(VisitType.PRODUCT_DETAIL)
    public RespBody<LineDetailVO> detail(@Validated IdDTO request) {
        LineDetailVO detail = lineService.detailById(request.getId());
        return RespBody.success(detail);
    }

    @GetMapping("/price/list")
    @Operation(summary = "线路日价格")
    public RespBody<List<LineConfigVO>> getPriceList(@Validated IdDTO dto) {
        List<LineConfigVO> voList = lineConfigService.getPriceList(dto.getId());
        return RespBody.success(voList);
    }
}
