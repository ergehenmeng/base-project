package com.eghm.app.webapp.controller;

import com.eghm.business.operation.delivery.service.DeliveryCacheService;
import com.eghm.foundation.core.configuration.authentication.ApiHolder;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.foundation.core.enums.Channel;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.business.operation.delivery.vo.BannerVO;
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
@Tag(name = "轮播图")
@AllArgsConstructor
@RequestMapping(value = "/webapp/banner", produces = MediaType.APPLICATION_JSON_VALUE)
public class BannerController {

    private final DeliveryCacheService deliveryCacheService;

    @GetMapping("/list")
    @Operation(summary = "查询可用的轮播图列表")
    @Parameter(name = "bannerType", description = "轮播图分类id", required = true)
    public RespBody<List<BannerVO>> list(@RequestParam("bannerType") Integer bannerType) {
        String channel = ApiHolder.getChannel();
        if (null == channel) {
            return RespBody.error(ErrorCode.CHANNEL_NULL);
        }
        List<BannerVO> bannerList = deliveryCacheService.getBanner(Channel.valueOf(channel), bannerType);
        return RespBody.success(bannerList);
    }

}
