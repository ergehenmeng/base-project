package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ItemStoreService;
import com.eghm.vo.business.item.store.ItemStoreHomeVO;
import com.eghm.vo.business.item.store.ItemStoreVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/22
 */
@RestController
@Api(tags = "店铺")
@AllArgsConstructor
@RequestMapping("/webapp/store")
public class ItemStoreController {

    private final ItemStoreService itemStoreService;

    @GetMapping("/home")
    @ApiOperation("店铺首页")
    public RespBody<ItemStoreHomeVO> home(@Validated IdDTO dto) {
        ItemStoreHomeVO detail = itemStoreService.homeDetail(dto.getId());
        return RespBody.success(detail);
    }

    @GetMapping("/recommend")
    @ApiOperation("推荐店铺列表")
    public RespBody<List<ItemStoreVO>> recommend() {
        List<ItemStoreVO> recommend = itemStoreService.getRecommend();
        return RespBody.success(recommend);
    }

}
