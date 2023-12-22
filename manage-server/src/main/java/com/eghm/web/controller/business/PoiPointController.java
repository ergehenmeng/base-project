package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.poi.PoiLineQueryRequest;
import com.eghm.dto.poi.PoiPointAddRequest;
import com.eghm.dto.poi.PoiPointEditRequest;
import com.eghm.model.PoiPoint;
import com.eghm.service.business.PoiPointService;
import com.eghm.vo.poi.PoiPointResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */
@RestController
@Api(tags = "poi点位信息")
@AllArgsConstructor
@RequestMapping("/manage/poi/point")
public class PoiPointController {

    private final PoiPointService poiPointService;

    @ApiOperation("列表")
    @GetMapping("/listPage")
    public RespBody<PageData<PoiPointResponse>> getByPage(PoiLineQueryRequest request) {
        Page<PoiPointResponse> byPage = poiPointService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @ApiOperation("创建")
    @PostMapping("/create")
    public RespBody<Void> create(@Validated @RequestBody PoiPointAddRequest request) {
        poiPointService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody PoiPointEditRequest request) {
        poiPointService.update(request);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        poiPointService.deleteById(dto.getId());
        return RespBody.success();
    }
}
