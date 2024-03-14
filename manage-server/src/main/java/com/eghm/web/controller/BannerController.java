package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.banner.BannerAddRequest;
import com.eghm.dto.banner.BannerEditRequest;
import com.eghm.dto.banner.BannerQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.Banner;
import com.eghm.service.common.BannerService;
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
    public RespBody<PageData<Banner>> listPage(BannerQueryRequest request) {
        Page<Banner> byPage = bannerService.getByPage(request);
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
}
