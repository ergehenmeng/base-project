package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.poi.LinePointBindRequest;
import com.eghm.dto.poi.PoiLineAddRequest;
import com.eghm.dto.poi.PoiLineEditRequest;
import com.eghm.dto.poi.PoiLineQueryRequest;
import com.eghm.service.business.PoiLineService;
import com.eghm.vo.poi.PoiLineResponse;
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
@Api(tags = "poi类线路")
@AllArgsConstructor
@RequestMapping("/manage/poi/line")
public class PoiLineController {

    private final PoiLineService poiLineService;

    @ApiOperation("列表")
    @GetMapping("/listPage")
    public RespBody<PageData<PoiLineResponse>> getByPage(PoiLineQueryRequest request) {
        Page<PoiLineResponse> byPage = poiLineService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @ApiOperation("创建")
    @PostMapping("/create")
    public RespBody<Void> create(@Validated @RequestBody PoiLineAddRequest request) {
        poiLineService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody PoiLineEditRequest request) {
        poiLineService.update(request);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        poiLineService.deleteById(dto.getId());
        return RespBody.success();
    }

    @PostMapping("/bind")
    @ApiOperation("绑定点位")
    public RespBody<Void> bind(@RequestBody @Validated LinePointBindRequest request) {
        poiLineService.bindPoint(request);
        return RespBody.success();
    }


}
