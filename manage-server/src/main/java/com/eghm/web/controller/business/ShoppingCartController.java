package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.shopping.ShoppingCartQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ShoppingCartService;
import com.eghm.vo.business.shopping.ShoppingCartResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
@RestController
@Api(tags = "购物车管理")
@AllArgsConstructor
@RequestMapping("/manage/shopping/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<ShoppingCartResponse>> listPage(ShoppingCartQueryRequest request) {
        Page<ShoppingCartResponse> byPage = shoppingCartService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

}
