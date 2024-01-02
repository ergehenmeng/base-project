package com.eghm.web.controller;

import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.Channel;
import com.eghm.model.Banner;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.banner.BannerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
@RestController
@Api(tags = "轮播图")
@AllArgsConstructor
@RequestMapping("/webapp/banner")
public class BannerController {

    private final CacheProxyService cacheProxyService;

    @GetMapping("/list")
    @ApiOperation("查询可用的轮播图列表")
    @ApiImplicitParam(name = "classify", value = "轮播图分类id")
    public RespBody<List<BannerVO>> list(@RequestParam("classify") Integer classify) {
        List<Banner> bannerList = cacheProxyService.getBanner(Channel.valueOf(ApiHolder.getChannel()), classify);
        return RespBody.success(DataUtil.copy(bannerList, BannerVO.class));
    }

}
