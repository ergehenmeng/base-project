package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.poi.PoiAreaAddRequest;
import com.eghm.dto.poi.PoiAreaEditRequest;
import com.eghm.dto.poi.StateRequest;
import com.eghm.model.PoiArea;
import com.eghm.service.business.PoiAreaService;
import com.eghm.vo.poi.BasePoiAreaResponse;
import com.eghm.vo.poi.PoiAreaResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */
@RestController
@Api(tags = "poi区域")
@AllArgsConstructor
@RequestMapping(value = "/manage/poi/area", produces = MediaType.APPLICATION_JSON_VALUE)
public class PoiAreaController {

    private final PoiAreaService poiAreaService;

    @ApiOperation("列表")
    @GetMapping("/listPage")
    public RespBody<PageData<PoiAreaResponse>> getByPage(PagingQuery request) {
        Page<PoiAreaResponse> byPage = poiAreaService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @ApiOperation("创建")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> create(@Validated @RequestBody PoiAreaAddRequest request) {
        poiAreaService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody PoiAreaEditRequest request) {
        poiAreaService.update(request);
        return RespBody.success();
    }

    @GetMapping(value = "/select")
    @ApiOperation("详情")
    public RespBody<PoiArea> select(@Validated IdDTO dto) {
        PoiArea required = poiAreaService.selectByIdRequired(dto.getId());
        return RespBody.success(required);
    }

    @PostMapping(value = "/updateState", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新状态")
    public RespBody<Void> updateState(@Validated @RequestBody StateRequest request) {
        poiAreaService.updateState(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        poiAreaService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/list")
    @ApiOperation("全部")
    public RespBody<List<BasePoiAreaResponse>> list() {
        List<BasePoiAreaResponse> byPage = poiAreaService.getList();
        return RespBody.success(byPage);
    }
}
