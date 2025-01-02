package com.eghm.web.controller;

import com.eghm.cache.CacheProxyService;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.Channel;
import com.eghm.enums.ErrorCode;
import com.eghm.vo.operate.banner.BannerVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name= "轮播图")
@AllArgsConstructor
@RequestMapping(value = "/webapp/banner", produces = MediaType.APPLICATION_JSON_VALUE)
public class BannerController {

    private final CacheProxyService cacheProxyService;

    @GetMapping("/list")
    @Operation(summary = "查询可用的轮播图列表")
    @Parameter(name = "bannerType", description = "轮播图分类id", required = true)
    public RespBody<List<BannerVO>> list(@RequestParam("bannerType") Integer bannerType) {
        String channel = ApiHolder.getChannel();
        if (null == channel) {
            return RespBody.error(ErrorCode.CHANNEL_NULL);
        }
        List<BannerVO> bannerList = cacheProxyService.getBanner(Channel.valueOf(channel), bannerType);
        return RespBody.success(bannerList);
    }

}
