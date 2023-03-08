package com.eghm.web.controller.business;

import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.shopping.AddCartDTO;
import com.eghm.model.dto.business.shopping.CartQuantityDTO;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.shopping.ShoppingCartVO;
import com.eghm.service.business.ShoppingCartService;
import com.eghm.web.annotation.Access;
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
    @Access
    public RespBody<Void> add(@RequestBody @Validated AddCartDTO dto) {
        shoppingCartService.add(dto);
        return RespBody.success();
    }

    @GetMapping("/list")
    @ApiOperation("购物车商品列表")
    @Access
    public List<ShoppingCartVO> list() {
        return shoppingCartService.getList(ApiHolder.getUserId());
    }

    @PostMapping("/delete")
    @ApiOperation("删除购物车商品")
    @Access
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        shoppingCartService.delete(dto.getId(), ApiHolder.getUserId());
        return RespBody.success();
    }

    @PostMapping("/quantity")
    @ApiOperation("更新商品数量")
    @Access
    public RespBody<Void> quantity(@RequestBody @Validated CartQuantityDTO dto) {
        shoppingCartService.updateQuantity(dto.getId(), dto.getQuantity(), ApiHolder.getUserId());
        return RespBody.success();
    }

}
