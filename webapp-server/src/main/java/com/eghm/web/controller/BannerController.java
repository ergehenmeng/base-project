package com.eghm.web.controller;

import com.eghm.cache.CacheProxyService;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.Channel;
import com.eghm.enums.ErrorCode;
import com.eghm.vo.banner.BannerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/4
 */
@RestController
@Api(tags = "轮播图")
@AllArgsConstructor
@RequestMapping(value = "/webapp/banner", produces = MediaType.APPLICATION_JSON_VALUE)
public class BannerController {

    private final CacheProxyService cacheProxyService;

    @GetMapping("/list")
    @ApiOperation("查询可用的轮播图列表")
    @ApiImplicitParam(name = "bannerType", value = "轮播图分类id", required = true)
    public RespBody<List<BannerVO>> list(@RequestParam("bannerType") Integer bannerType) {
        String channel = ApiHolder.getChannel();
        if (null == channel) {
            return RespBody.error(ErrorCode.CHANNEL_NULL);
        }
        List<BannerVO> bannerList = cacheProxyService.getBanner(Channel.valueOf(channel), bannerType);
        return RespBody.success(bannerList);
    }

}
