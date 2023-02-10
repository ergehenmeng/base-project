package com.eghm.web.controller.business;

import com.eghm.model.dto.IdDTO;
import com.eghm.model.vo.business.product.store.ProductStoreHomeVO;
import com.eghm.model.vo.business.product.store.ProductStoreVO;
import com.eghm.service.business.ProductStoreService;
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
public class ProductStoreController {

    private final ProductStoreService productStoreService;

    @GetMapping("/home")
    @ApiOperation("店铺首页")
    public ProductStoreHomeVO home(@Validated IdDTO dto) {
        return productStoreService.homeDetail(dto.getId());
    }

    @GetMapping("/recommend")
    @ApiOperation("推荐店铺列表")
    public List<ProductStoreVO> recommend() {
        return productStoreService.getRecommend();
    }

}
