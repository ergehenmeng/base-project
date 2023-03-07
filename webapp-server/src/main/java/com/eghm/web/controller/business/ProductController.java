package com.eghm.web.controller.business;

import com.eghm.model.dto.business.product.ItemQueryDTO;
import com.eghm.model.dto.business.product.ItemCouponQueryDTO;
import com.eghm.model.vo.business.product.ProductListVO;
import com.eghm.model.vo.coupon.CouponListVO;
import com.eghm.service.business.CouponConfigService;
import com.eghm.service.business.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2023/1/23
 */

@RestController
@Api(tags = "商品信息")
@AllArgsConstructor
@RequestMapping("/webapp/product")
public class ProductController {

    private final ProductService productService;

    private final CouponConfigService couponConfigService;

    @GetMapping("/listPage")
    @ApiOperation("商品列表")
    public List<ProductListVO> listPage(ItemQueryDTO dto) {
        return productService.getByPage(dto);
    }

    @GetMapping("/recommend")
    @ApiOperation("商品推荐列表")
    public List<ProductListVO> recommend() {
        return productService.getRecommend();
    }

    @GetMapping("/coupon")
    @ApiOperation("商品页可以领取的优惠券")
    @ApiImplicitParam(name = "productId", value = "商品id", required = true)
    public List<CouponListVO> product(@RequestParam("productId") Long productId) {
        return couponConfigService.getProductCoupon(productId);
    }

    @GetMapping("/couponScope")
    @ApiOperation("优惠券匹配的商品列表")
    public List<ProductListVO> couponScope(@Validated ItemCouponQueryDTO dto) {
        return productService.getCouponScopeByPage(dto);
    }
}
