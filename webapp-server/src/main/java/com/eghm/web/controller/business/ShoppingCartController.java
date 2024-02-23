package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.shopping.AddCartDTO;
import com.eghm.dto.business.shopping.CartQuantityDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ShoppingCartService;
import com.eghm.vo.business.shopping.ShoppingCartVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@AccessToken
@RestController
@Api(tags = "购物车")
@AllArgsConstructor
@RequestMapping(value = "/webapp/shopping/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("添加购物车")
    public RespBody<Void> add(@RequestBody @Validated AddCartDTO dto) {
        shoppingCartService.add(dto);
        return RespBody.success();
    }

    @GetMapping("/list")
    @ApiOperation("购物车商品列表")
    public RespBody<List<ShoppingCartVO>> list() {
        List<ShoppingCartVO> voList = shoppingCartService.getList(ApiHolder.getMemberId());
        return RespBody.success(voList);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除购物车商品")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        shoppingCartService.delete(dto.getId(), ApiHolder.getMemberId());
        return RespBody.success();
    }

    @PostMapping(value = "/quantity", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新商品数量")
    public RespBody<Void> quantity(@RequestBody @Validated CartQuantityDTO dto) {
        shoppingCartService.updateQuantity(dto.getId(), dto.getQuantity(), ApiHolder.getMemberId());
        return RespBody.success();
    }

}
