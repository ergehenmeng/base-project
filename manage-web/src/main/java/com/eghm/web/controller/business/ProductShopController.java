package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.ProductShop;
import com.eghm.model.dto.business.product.shop.ProductShopQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.service.business.ProductShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2022/7/22
 */
@RestController
@Api(tags = "商铺信息")
@AllArgsConstructor
@RequestMapping("/product/shop")
public class ProductShopController {

    private final ProductShopService productShopService;

    @GetMapping("/listPage")
    @ApiOperation("商铺列表")
    public PageData<ProductShop> listPage(ProductShopQueryRequest request) {
        Page<ProductShop> byPage = productShopService.getByPage(request);
        return PageData.toPage(byPage);
    }

}
