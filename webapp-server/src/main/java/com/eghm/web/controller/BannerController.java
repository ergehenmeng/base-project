package com.eghm.web.controller;

import com.eghm.common.enums.Channel;
import com.eghm.model.Banner;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.banner.BannerVO;
import com.eghm.service.common.BannerService;
import com.eghm.utils.DataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
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

    private final BannerService bannerService;

    @GetMapping("/list")
    @ApiOperation("查询可用的轮播图列表")
    @ApiImplicitParam(name = "classify", value = "轮播图分类id")
    public RespBody<List<BannerVO>> list(@NotNull(message = "轮播图类型不能为空") @RequestParam("classify") Byte classify) {
        List<Banner> bannerList = bannerService.getBanner(Channel.valueOf(ApiHolder.getChannel()), classify);
        return RespBody.success(DataUtil.convert(bannerList, banner -> DataUtil.copy(banner, BannerVO.class)));
    }

}
