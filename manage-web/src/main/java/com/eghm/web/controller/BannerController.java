package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.Banner;
import com.eghm.model.dto.banner.BannerAddRequest;
import com.eghm.model.dto.banner.BannerEditRequest;
import com.eghm.model.dto.banner.BannerQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.common.BannerService;
import com.eghm.service.common.FileService;
import com.eghm.web.annotation.Mark;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author 二哥很猛
 * @date 2019/8/22 11:22
 */
@RestController
@Api(tags = "banner管理")
@AllArgsConstructor
@RequestMapping("/banner")
public class BannerController {

    private final BannerService bannerService;

    private final FileService fileService;

    /**
     * 分页查询轮播图配置信息
     */
    @GetMapping("/listPage")
    @ApiOperation("轮播图列表")
    public RespBody<PageData<Banner>> listPage(BannerQueryRequest request) {
        Page<Banner> byPage = bannerService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    /**
     * 新增轮播图信息
     */
    @PostMapping("/add")
    @Mark
    @ApiOperation("添加轮播图")
    @ApiImplicitParam(name = "imgFile", dataType = "file", paramType = "formData", value = "图片", required = true)
    public RespBody<Void> add(@Valid BannerAddRequest request, @RequestParam("imgFile") MultipartFile imgFile) {
        request.setImgUrl(fileService.saveFile(imgFile).getPath());
        bannerService.addBanner(request);
        return RespBody.success();
    }

    /**
     * 编辑轮播图信息
     */
    @PostMapping("/edit")
    @Mark
    @ApiOperation("修改轮播图")
    @ApiImplicitParam(name = "imgFile", dataType = "file", paramType = "formData", value = "图片")
    public RespBody<Void> edit(@Valid BannerEditRequest request, @RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
        if (imgFile != null) {
            request.setImgUrl(fileService.saveFile(imgFile).getPath());
        }
        bannerService.editBanner(request);
        return RespBody.success();
    }

}
