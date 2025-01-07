package com.eghm.web.controller.business;

import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.PoiLineService;
import com.eghm.service.business.PoiPointService;
import com.eghm.service.business.PoiTypeService;
import com.eghm.vo.poi.PoiLineVO;
import com.eghm.vo.poi.PoiPointVO;
import com.eghm.vo.poi.PoiTypeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/22
 */

@RestController
@Tag(name="景区导览")
@AllArgsConstructor
@RequestMapping(value = "/webapp/poi", produces = MediaType.APPLICATION_JSON_VALUE)
public class PoiController {

    private final PoiTypeService poiTypeService;

    private final PoiLineService poiLineService;

    private final PoiPointService poiPointService;

    @GetMapping("/typeList")
    @Operation(summary = "poi类型列表")
    @Parameter(name = "areaCode", description = "区域编码", required = true)
    public RespBody<List<PoiTypeResponse>> typeList(@RequestParam("areaCode") String areaCode) {
        List<PoiTypeResponse> serviceList = poiTypeService.getList(areaCode);
        return RespBody.success(serviceList);
    }

    @GetMapping("/pointList")
    @Operation(summary = "poi点位列表(类型)")
    @Parameter(name = "typeId", description = "区域类型id", required = true)
    public RespBody<List<PoiPointVO>> pointList(@RequestParam("typeId") Long typeId) {
        List<PoiPointVO> pointList = poiPointService.pointList(typeId);
        return RespBody.success(pointList);
    }

    @GetMapping("/lineList")
    @Operation(summary = "poi线路列表")
    @Parameter(name = "areaCode", description = "区域编码", required = true)
    public RespBody<List<PoiLineVO>> lineList(@RequestParam("areaCode") String areaCode) {
        List<PoiLineVO> pointList = poiLineService.getList(areaCode);
        return RespBody.success(pointList);
    }

    @GetMapping("/pointSearch")
    @Operation(summary = "poi点位列表(搜索)")
    @Parameter(name = "areaCode", description = "区域编号", required = true)
    @Parameter(name = "queryName", description = "搜索条件")
    public RespBody<List<PoiPointVO>> pointSearch(@RequestParam("areaCode") String areaCode, @RequestParam(value = "queryName", required = false) String queryName) {
        List<PoiPointVO> pointList = poiPointService.searchPointList(areaCode, queryName);
        return RespBody.success(pointList);
    }

}
