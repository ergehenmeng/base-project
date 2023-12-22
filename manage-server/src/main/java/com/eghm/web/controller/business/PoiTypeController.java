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
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */
@RestController
@Api(tags = "poi类型信息")
@AllArgsConstructor
@RequestMapping("/manage/poi/type")
public class PoiTypeController {

    private final PoiTypeService poiTypeService;

    @ApiOperation("列表")
    @GetMapping("/listPage")
    public RespBody<PageData<PoiTypeResponse>> getByPage(PoiTypeQueryRequest request) {
        Page<PoiTypeResponse> byPage = poiTypeService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @ApiOperation("创建poi类型")
    @PostMapping("/create")
    public RespBody<Void> create(@Validated @RequestBody PoiTypeAddRequest request) {
        poiTypeService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新poi类型")
    public RespBody<Void> update(@Validated @RequestBody PoiTypeEditRequest request) {
        poiTypeService.update(request);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        poiTypeService.deleteById(dto.getId());
        return RespBody.success();
    }
}
