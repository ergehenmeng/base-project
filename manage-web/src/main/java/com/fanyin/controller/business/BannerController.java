package com.fanyin.controller.business;

import com.fanyin.controller.AbstractController;
import com.fanyin.dao.model.business.Banner;
import com.fanyin.model.dto.business.banner.BannerAddRequest;
import com.fanyin.model.dto.business.banner.BannerEditRequest;
import com.fanyin.model.dto.business.banner.BannerQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.common.BannerService;
import com.fanyin.service.common.FileService;
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

    /**
     * 分页查询轮播图配置信息
     */
    @PostMapping("/business/banner/list_page")
    @ResponseBody
    public Paging<Banner> listPage(BannerQueryRequest request){
        PageInfo<Banner> byPage = bannerService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 轮播图编辑页面
     */
    @GetMapping("/business/banner/edit_page")
    public String editPage(Model model, Integer id){
        Banner banner = bannerService.getById(id);
        model.addAttribute("banner",banner);
        return "business/banner/edit_page";
    }

    /**
     * 新增轮播图信息
     */
    @PostMapping("/business/banner/add")
    @ResponseBody
    public RespBody add(BannerAddRequest request, @RequestParam("imgFile") MultipartFile imgFile){
        request.setImgUrl(fileService.saveFile(imgFile).getPath());
        bannerService.addBanner(request);
        return RespBody.getInstance();
    }

    /**
     * 编辑轮播图信息
     */
    @PostMapping("/business/banner/edit")
    @ResponseBody
    public RespBody edit(BannerEditRequest request, @RequestParam(value = "imgFile",required = false) MultipartFile imgFile){
        if(imgFile != null){
            request.setImgUrl(fileService.saveFile(imgFile).getPath());
        }
        bannerService.editBanner(request);
        return RespBody.getInstance();
    }


}
