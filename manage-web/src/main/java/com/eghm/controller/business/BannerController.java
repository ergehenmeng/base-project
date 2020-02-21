package com.eghm.controller.business;

import com.eghm.constants.DictConstant;
import com.eghm.controller.AbstractController;
import com.eghm.dao.model.business.Banner;
import com.eghm.model.dto.business.banner.BannerAddRequest;
import com.eghm.model.dto.business.banner.BannerEditRequest;
import com.eghm.model.dto.business.banner.BannerQueryRequest;
import com.eghm.model.ext.Paging;
import com.eghm.model.ext.RespBody;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.common.BannerService;
import com.eghm.service.common.FileService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 二哥很猛
 * @date 2019/8/22 11:22
 */
@Controller
public class BannerController extends AbstractController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private FileService fileService;

    @Autowired
    private CacheProxyService cacheProxyService;

    /**
     * 分页查询轮播图配置信息
     */
    @PostMapping("/business/banner/list_page")
    @ResponseBody
    public Paging<Banner> listPage(BannerQueryRequest request) {
        PageInfo<Banner> byPage = bannerService.getByPage(request);
        return DataUtil.transform(byPage, banner -> {
            banner.setClassifyName(cacheProxyService.getDictValue(DictConstant.BANNER_CLASSIFY, banner.getClassify()));
            return banner;
        });
    }

    /**
     * 轮播图编辑页面
     */
    @GetMapping("/business/banner/edit_page")
    public String editPage(Model model, Integer id) {
        Banner banner = bannerService.getById(id);
        model.addAttribute("banner", banner);
        return "business/banner/edit_page";
    }

    /**
     * 新增轮播图信息
     */
    @PostMapping("/business/banner/add")
    @ResponseBody
    public RespBody<Object> add(BannerAddRequest request, @RequestParam("imgFile") MultipartFile imgFile) {
        request.setImgUrl(fileService.saveFile(imgFile).getPath());
        bannerService.addBanner(request);
        return RespBody.getInstance();
    }

    /**
     * 编辑轮播图信息
     */
    @PostMapping("/business/banner/edit")
    @ResponseBody
    public RespBody<Object> edit(BannerEditRequest request, @RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
        if (imgFile != null) {
            request.setImgUrl(fileService.saveFile(imgFile).getPath());
        }
        bannerService.editBanner(request);
        return RespBody.getInstance();
    }


}
