package com.eghm.web.controller;

import com.eghm.constants.DictConstant;
import com.eghm.dao.model.Banner;
import com.eghm.model.dto.banner.BannerAddRequest;
import com.eghm.model.dto.banner.BannerEditRequest;
import com.eghm.model.dto.banner.BannerQueryRequest;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.cache.ProxyService;
import com.eghm.service.common.BannerService;
import com.eghm.service.common.FileService;
import com.eghm.utils.DataUtil;
import com.eghm.web.annotation.Mark;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 二哥很猛
 * @date 2019/8/22 11:22
 */
@RestController
public class BannerController {

    private BannerService bannerService;

    private FileService fileService;

    private ProxyService proxyService;

    @Autowired
    public void setBannerService(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setProxyService(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    /**
     * 分页查询轮播图配置信息
     */
    @GetMapping("/banner/list_page")
    public Paging<Banner> listPage(BannerQueryRequest request) {
        PageInfo<Banner> byPage = bannerService.getByPage(request);
        return DataUtil.convert(byPage, banner -> {
            String dictValue = proxyService.getDictValue(DictConstant.BANNER_CLASSIFY, banner.getClassify());
            banner.setClassifyName(dictValue);
            return banner;
        });
    }

    /**
     * 轮播图编辑页面
     */
    @GetMapping("/banner/edit_page")
    public String editPage(Model model, Long id) {
        Banner banner = bannerService.getById(id);
        model.addAttribute("banner", banner);
        return "banner/edit_page";
    }

    /**
     * 新增轮播图信息
     */
    @PostMapping("/banner/add")
    @Mark
    public RespBody<Object> add(BannerAddRequest request, @RequestParam("imgFile") MultipartFile imgFile) {
        request.setImgUrl(fileService.saveFile(imgFile).getPath());
        bannerService.addBanner(request);
        return RespBody.success();
    }

    /**
     * 编辑轮播图信息
     */
    @PostMapping("/banner/edit")
    @Mark
    public RespBody<Object> edit(BannerEditRequest request, @RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
        if (imgFile != null) {
            request.setImgUrl(fileService.saveFile(imgFile).getPath());
        }
        bannerService.editBanner(request);
        return RespBody.success();
    }

}
