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
@Api(tags = "poi区域信息")
@AllArgsConstructor
@RequestMapping("/manage/poi/area")
public class PoiAreaController {

    private final PoiAreaService poiAreaService;

    @ApiOperation("列表")
    @GetMapping("/listPage")
    public RespBody<PageData<PoiArea>> getByPage(PagingQuery request) {
        Page<PoiArea> byPage = poiAreaService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @ApiOperation("创建区域")
    @PostMapping("/create")
    public RespBody<Void> create(@Validated @RequestBody PoiAreaAddRequest request) {
        poiAreaService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新区域")
    public RespBody<Void> update(@Validated @RequestBody PoiAreaEditRequest request) {
        poiAreaService.update(request);
        return RespBody.success();
    }

    @PostMapping("/updateState")
    @ApiOperation("更新状态")
    public RespBody<Void> updateState(@Validated @RequestBody StateRequest request) {
        poiAreaService.updateState(request);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        poiAreaService.deleteById(dto.getId());
        return RespBody.success();
    }
}
