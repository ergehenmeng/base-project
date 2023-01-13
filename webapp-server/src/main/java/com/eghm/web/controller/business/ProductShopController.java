package com.eghm.web.controller.business;

import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.product.shop.ProductShopAddRequest;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.business.product.shop.ProductShopHomeVO;
import com.eghm.service.business.ProductShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2022/7/22
 */
@RestController
@Api(tags = "店铺")
@AllArgsConstructor
@RequestMapping("/webapp/product/shop")
public class ProductShopController {

    private final ProductShopService productShopService;

    @GetMapping("/home")
    @ApiOperation("店铺首页")
    public ProductShopHomeVO home(@Validated IdDTO dto) {
        return productShopService.homeDetail(dto.getId());
    }

    @GetMapping("/listPage")
    @ApiOperation("商品列表")
    public RespBody<Void> listPage(@Validated @RequestBody ProductShopAddRequest request) {
        productShopService.create(request);
        return RespBody.success();
    }

}
