package com.eghm.web.controller.business;

import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.PoiLineService;
import com.eghm.service.business.PoiPointService;
import com.eghm.service.business.PoiTypeService;
import com.eghm.vo.poi.PoiLineVO;
import com.eghm.vo.poi.PoiPointVO;
import com.eghm.vo.poi.PoiTypeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
@Api(tags = "景区导览")
@AllArgsConstructor
@RequestMapping("/webapp/poi")
public class PoiController {

    private final PoiTypeService poiTypeService;

    private final PoiLineService poiLineService;

    private final PoiPointService poiPointService;

    @GetMapping("/typeList")
    @ApiOperation("poi类型列表")
    @ApiImplicitParam(name = "areaCode", value = "区域编码", required = true)
    public RespBody<List<PoiTypeResponse>> list(@RequestParam("areaCode") String areaCode) {
        List<PoiTypeResponse> serviceList = poiTypeService.getList(areaCode);
        return RespBody.success(serviceList);
    }

    @GetMapping("/pointList")
    @ApiOperation("poi点位列表")
    @ApiImplicitParam(name = "typeId", value = "区域类型id", required = true)
    public RespBody<List<PoiPointVO>> list(@RequestParam("typeId") Long typeId) {
        List<PoiPointVO> pointList = poiPointService.pointList(typeId);
        return RespBody.success(pointList);
    }

    @GetMapping("/lineList")
    @ApiOperation("poi线路列表")
    @ApiImplicitParam(name = "areaCode", value = "区域编码", required = true)
    public RespBody<List<PoiLineVO>> lineList(@RequestParam("areaCode") String areaCode) {
        List<PoiLineVO> pointList = poiLineService.getList(areaCode);
        return RespBody.success(pointList);
    }
}
