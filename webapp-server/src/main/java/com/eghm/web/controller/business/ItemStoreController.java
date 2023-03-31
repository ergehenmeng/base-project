package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.vo.business.item.store.ItemStoreHomeVO;
import com.eghm.vo.business.item.store.ItemStoreVO;
import com.eghm.service.business.ItemStoreService;
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
    public ItemStoreHomeVO home(@Validated IdDTO dto) {
        return itemStoreService.homeDetail(dto.getId());
    }

    @GetMapping("/recommend")
    @ApiOperation("推荐店铺列表")
    public List<ItemStoreVO> recommend() {
        return itemStoreService.getRecommend();
    }

}
