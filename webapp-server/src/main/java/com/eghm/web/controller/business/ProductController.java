package com.eghm.web.controller.business;

import com.eghm.model.dto.business.product.ProductQueryDTO;
import com.eghm.model.vo.business.product.ProductListVO;
import com.eghm.service.business.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/listPage")
    @ApiOperation("商品列表")
    public List<ProductListVO> listPage(ProductQueryDTO dto) {
        return productService.getByPage(dto);
    }

    @GetMapping("/recommend")
    @ApiOperation("商品推荐列表")
    public List<ProductListVO> recommend() {
        return productService.getRecommend();
    }
}
