package com.eghm.web.controller.operate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.operate.banner.BannerAddRequest;
import com.eghm.dto.operate.banner.BannerEditRequest;
import com.eghm.dto.operate.banner.BannerQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.operate.BannerService;
import com.eghm.vo.banner.BannerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2019/8/22 11:22
 */
@RestController
@Api(tags = "banner管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/banner", produces = MediaType.APPLICATION_JSON_VALUE)
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<BannerResponse>> listPage(BannerQueryRequest request) {
        Page<BannerResponse> byPage = bannerService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody BannerAddRequest request) {
        bannerService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody BannerEditRequest request) {
        bannerService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        bannerService.deleteById(request.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("排序")
    public RespBody<Void> sort(@Validated @RequestBody SortByDTO request) {
        bannerService.sort(request.getId(), request.getSortBy());
        return RespBody.success();
    }

    @PostMapping(value = "/updateState", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新状态")
    public RespBody<Void> updateState(@Validated @RequestBody SortByDTO.StateRequest request) {
        bannerService.updateState(request.getId(), request.getState());
        return RespBody.success();
    }
}
