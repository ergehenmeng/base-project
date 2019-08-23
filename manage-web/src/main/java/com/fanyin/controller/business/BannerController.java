package com.fanyin.controller.business;

import com.fanyin.controller.AbstractUploadController;
import com.fanyin.dao.model.business.Banner;
import com.fanyin.model.dto.business.banner.BannerAddRequest;
import com.fanyin.model.dto.business.banner.BannerEditRequest;
import com.fanyin.model.dto.business.banner.BannerQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.common.BannerService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 二哥很猛
 * @date 2019/8/22 11:22
 */
@RestController
public class BannerController extends AbstractUploadController {

    @Autowired
    private BannerService bannerService;

    /**
     * 分页查询轮播图配置信息
     */
    @RequestMapping("/business/banner/list_page")
    public Paging<Banner> listPage(BannerQueryRequest request){
        PageInfo<Banner> byPage = bannerService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 新增轮播图信息
     */
    @RequestMapping("/business/banner/add")
    public RespBody add(BannerAddRequest request, @RequestParam("imgFile") MultipartFile imgFile){
        request.setImgUrl(super.saveFile(imgFile));
        bannerService.addBanner(request);
        return RespBody.getInstance();
    }

    /**
     * 编辑轮播图信息
     */
    @RequestMapping("/business/banner/edit")
    public RespBody edit(BannerEditRequest request, @RequestParam(value = "imgFile",required = false) MultipartFile imgFile){
        if(imgFile != null){
            request.setImgUrl(super.saveFile(imgFile));
        }
        return RespBody.getInstance();
    }


}
