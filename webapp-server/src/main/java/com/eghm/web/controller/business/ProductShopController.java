package com.eghm.web.controller.business;

import com.eghm.model.dto.IdDTO;
import com.eghm.model.vo.business.product.shop.ProductShopHomeVO;
import com.eghm.model.vo.business.product.shop.ProductShopVO;
import com.eghm.service.business.ProductShopService;
import com.eghm.web.annotation.SkipAccess;
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
@RequestMapping("/webapp/shop")
public class ProductShopController {

    private final ProductShopService productShopService;

    @GetMapping("/home")
    @ApiOperation("店铺首页")
    @SkipAccess
    public ProductShopHomeVO home(@Validated IdDTO dto) {
        return productShopService.homeDetail(dto.getId());
    }

    @GetMapping("/recommend")
    @ApiOperation("推荐店铺列表")
    @SkipAccess
    public List<ProductShopVO> recommend() {
        return productShopService.getRecommend();
    }

}
