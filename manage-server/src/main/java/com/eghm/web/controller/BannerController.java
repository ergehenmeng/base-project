package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.Banner;
import com.eghm.model.dto.banner.BannerAddRequest;
import com.eghm.model.dto.banner.BannerEditRequest;
import com.eghm.model.dto.banner.BannerQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.common.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2019/8/22 11:22
 */
@RestController
@Api(tags = "banner管理")
@AllArgsConstructor
@RequestMapping("/manage/banner")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/listPage")
    @ApiOperation("轮播图列表")
    public PageData<Banner> listPage(BannerQueryRequest request) {
        Page<Banner> byPage = bannerService.getByPage(request);
        return PageData.toPage(byPage);
    }

    @PostMapping("/create")
    @ApiOperation("添加轮播图")
    public RespBody<Void> create(@Validated @RequestBody BannerAddRequest request) {
        bannerService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改轮播图")
    public RespBody<Void> update(@Validated @RequestBody BannerEditRequest request) {
        bannerService.update(request);
        return RespBody.success();
    }

}
