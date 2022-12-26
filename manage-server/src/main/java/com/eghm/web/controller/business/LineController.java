package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.Line;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.line.LineAddRequest;
import com.eghm.model.dto.business.line.LineEditRequest;
import com.eghm.model.dto.business.line.LineQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.business.LineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2022/11/26
 */
@RestController
@Api(tags = "线路")
@AllArgsConstructor
@RequestMapping("/manage/line")
public class LineController {

    private final LineService lineService;

    @ApiOperation("查询线路列表")
    @GetMapping("/listPage")
    public PageData<Line> getByPage(LineQueryRequest request) {
        Page<Line> scenicPage = lineService.getByPage(request);
        return PageData.toPage(scenicPage);
    }

    @ApiOperation("创建线路")
    @PostMapping("/create")
    public RespBody<Void> create(@Validated @RequestBody LineAddRequest request) {
        lineService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新线路")
    public RespBody<Void> update(@Validated @RequestBody LineEditRequest request) {
        lineService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public Line select(@Validated IdDTO request) {
        return lineService.selectById(request.getId());
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        lineService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        lineService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformAudit")
    @ApiOperation("平台上架审核")
    public RespBody<Void> updateState(@RequestBody @Validated IdDTO dto) {
        lineService.updateAuditState(dto.getId(), PlatformState.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        lineService.updateAuditState(dto.getId(), PlatformState.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        lineService.deleteById(dto.getId());
        return RespBody.success();
    }
}
