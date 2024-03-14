package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.poi.PoiTypeAddRequest;
import com.eghm.dto.poi.PoiTypeEditRequest;
import com.eghm.dto.poi.PoiTypeQueryRequest;
import com.eghm.service.business.PoiTypeService;
import com.eghm.vo.poi.PoiTypeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@Api(tags = "poi类型")
@AllArgsConstructor
@RequestMapping(value = "/manage/poi/type", produces = MediaType.APPLICATION_JSON_VALUE)
public class PoiTypeController {

    private final PoiTypeService poiTypeService;

    @ApiOperation("列表")
    @GetMapping("/listPage")
    public RespBody<PageData<PoiTypeResponse>> getByPage(PoiTypeQueryRequest request) {
        Page<PoiTypeResponse> byPage = poiTypeService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @ApiOperation("新增")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> create(@Validated @RequestBody PoiTypeAddRequest request) {
        poiTypeService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody PoiTypeEditRequest request) {
        poiTypeService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        poiTypeService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/list")
    @ApiOperation("全部")
    @ApiImplicitParam(name = "areaCode", value = "区域编码", required = true)
    public RespBody<List<PoiTypeResponse>> list(@RequestParam("areaCode") String areaCode) {
        List<PoiTypeResponse> serviceList = poiTypeService.getList(areaCode);
        return RespBody.success(serviceList);
    }
}
