package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.Scenic;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.scenic.ScenicAddRequest;
import com.eghm.model.dto.business.scenic.ScenicEditRequest;
import com.eghm.model.dto.business.scenic.ScenicQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.business.ScenicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛 2022/6/17 19:06
 */
@RestController
@Api(tags = "景区")
@AllArgsConstructor
@RequestMapping("/manage/scenic")
public class ScenicController {

    private final ScenicService scenicService;

    @ApiOperation("查询景区列表")
    @GetMapping("/listPage")
    public PageData<Scenic> getByPage(ScenicQueryRequest request) {
        Page<Scenic> scenicPage = scenicService.getByPage(request);
        return PageData.toPage(scenicPage);
    }

    @ApiOperation("创建景区")
    @PostMapping("/create")
    public RespBody<Void> create(@Validated @RequestBody ScenicAddRequest request) {
        scenicService.createScenic(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新景区")
    public RespBody<Void> update(@Validated @RequestBody ScenicEditRequest request) {
        scenicService.updateScenic(request);
        return RespBody.success();
    }

    @PostMapping("/select")
    @ApiOperation("详情")
    public Scenic select(@Validated @RequestBody IdDTO request) {
        return scenicService.selectById(request.getId());
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        scenicService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        scenicService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformAudit")
    @ApiOperation("平台上架审核")
    public RespBody<Void> updateState(@RequestBody @Validated IdDTO dto) {
        scenicService.updateAuditState(dto.getId(), PlatformState.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        scenicService.updateAuditState(dto.getId(), PlatformState.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        scenicService.deleteById(dto.getId());
        return RespBody.success();
    }
}
