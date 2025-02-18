package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.poi.LinePointBindRequest;
import com.eghm.dto.poi.PoiLineAddRequest;
import com.eghm.dto.poi.PoiLineEditRequest;
import com.eghm.dto.poi.PoiLineQueryRequest;
import com.eghm.model.PoiLine;
import com.eghm.service.business.PoiLineService;
import com.eghm.vo.poi.LinePointResponse;
import com.eghm.vo.poi.PoiLineResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */
@RestController
@Tag(name="poi线路")
@AllArgsConstructor
@RequestMapping(value = "/manage/poi/line", produces = MediaType.APPLICATION_JSON_VALUE)
public class PoiLineController {

    private final PoiLineService poiLineService;

    @Operation(summary = "列表")
    @GetMapping("/listPage")
    public RespBody<PageData<PoiLineResponse>> getByPage(PoiLineQueryRequest request) {
        Page<PoiLineResponse> byPage = poiLineService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @Operation(summary = "创建")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> create(@Validated @RequestBody PoiLineAddRequest request) {
        poiLineService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新")
    public RespBody<Void> update(@Validated @RequestBody PoiLineEditRequest request) {
        poiLineService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        poiLineService.deleteById(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/bind", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "绑定点位")
    public RespBody<Void> bind(@RequestBody @Validated LinePointBindRequest request) {
        poiLineService.bindPoint(request);
        return RespBody.success();
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        poiLineService.updateState(dto.getId(), 1);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        poiLineService.updateState(dto.getId(), 0);
        return RespBody.success();
    }

    @GetMapping("/bindDetail")
    @Operation(summary = "绑定详情")
    public RespBody<LinePointResponse> bindDetail(@Validated IdDTO dto) {
        LinePointResponse response = poiLineService.getLinePoint(dto.getId());
        return RespBody.success(response);
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<PoiLine> select(@Validated IdDTO dto) {
        PoiLine poiLine = poiLineService.selectByIdRequired(dto.getId());
        return RespBody.success(poiLine);
    }

}
