package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.poi.PoiPointAddRequest;
import com.eghm.dto.poi.PoiPointEditRequest;
import com.eghm.dto.poi.PoiPointQueryRequest;
import com.eghm.model.PoiPoint;
import com.eghm.service.business.PoiPointService;
import com.eghm.vo.poi.PoiPointResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */
@RestController
@Api(tags = "poi点位信息")
@AllArgsConstructor
@RequestMapping(value = "/manage/poi/point", produces = MediaType.APPLICATION_JSON_VALUE)
public class PoiPointController {

    private final PoiPointService poiPointService;

    @ApiOperation("列表")
    @GetMapping("/listPage")
    public RespBody<PageData<PoiPointResponse>> getByPage(PoiPointQueryRequest request) {
        Page<PoiPointResponse> byPage = poiPointService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @ApiOperation("创建")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> create(@Validated @RequestBody PoiPointAddRequest request) {
        poiPointService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody PoiPointEditRequest request) {
        poiPointService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        poiPointService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<PoiPoint> select(@Validated IdDTO dto) {
        PoiPoint poiPoint = poiPointService.selectByIdRequired(dto.getId());
        return RespBody.success(poiPoint);
    }
}
