package com.fanyin.controller.business;

import com.fanyin.dao.model.business.Banner;
import com.fanyin.model.dto.business.banner.BannerQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.service.common.BannerService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2019/8/22 11:22
 */
@RestController
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * 分页查询轮播图配置信息
     */
    @RequestMapping("/banner/list_page")
    public Paging<Banner> listPage(BannerQueryRequest request){
        PageInfo<Banner> byPage = bannerService.getByPage(request);
        return new Paging<>(byPage);
    }
}
