package com.eghm.controller;

import com.eghm.common.enums.Channel;
import com.eghm.dao.model.Banner;
import com.eghm.model.dto.banner.BannerQueryDTO;
import com.eghm.model.ext.ApiHolder;
import com.eghm.model.ext.RespBody;
import com.eghm.model.vo.banner.BannerVO;
import com.eghm.service.common.BannerService;
import com.eghm.utils.DataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
@RestController
@Api("轮播图")
public class BannerController {

    private BannerService bannerService;

    @Autowired
    public void setBannerService(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    /**
     * 按类型查询的轮播图列表
     */
    @PostMapping("/banner/list")
    @ApiOperation("查询可用的轮播图列表")
    public RespBody<List<BannerVO>> list(BannerQueryDTO dto) {
        List<Banner> bannerList = bannerService.getBanner(Channel.valueOf(ApiHolder.getChannel()), dto.getClassify());
        return RespBody.success(DataUtil.convert(bannerList, banner -> DataUtil.copy(banner, BannerVO.class)));
    }


}
