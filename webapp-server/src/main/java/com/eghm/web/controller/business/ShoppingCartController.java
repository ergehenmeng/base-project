package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.shopping.AddCartDTO;
import com.eghm.dto.business.shopping.CartQuantityDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.vo.business.shopping.ShoppingCartVO;
import com.eghm.service.business.ShoppingCartService;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
@RestController
@Api(tags = "购物车")
@AllArgsConstructor
@RequestMapping("/webapp/shopping/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("添加购物车")
    @AccessToken
    public RespBody<Void> add(@RequestBody @Validated AddCartDTO dto) {
        shoppingCartService.add(dto);
        return RespBody.success();
    }

    @GetMapping("/list")
    @ApiOperation("购物车商品列表")
    @AccessToken
    public List<ShoppingCartVO> list() {
        return shoppingCartService.getList(ApiHolder.getMemberId());
    }

    @PostMapping("/delete")
    @ApiOperation("删除购物车商品")
    @AccessToken
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        shoppingCartService.delete(dto.getId(), ApiHolder.getMemberId());
        return RespBody.success();
    }

    @PostMapping("/quantity")
    @ApiOperation("更新商品数量")
    @AccessToken
    public RespBody<Void> quantity(@RequestBody @Validated CartQuantityDTO dto) {
        shoppingCartService.updateQuantity(dto.getId(), dto.getQuantity(), ApiHolder.getMemberId());
        return RespBody.success();
    }

}
