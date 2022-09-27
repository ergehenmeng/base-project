package com.eghm.web.controller.business;

import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.shopping.AddCarDTO;
import com.eghm.model.dto.business.shopping.CarQuantityDTO;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.shopping.ShoppingCarVO;
import com.eghm.service.business.ShoppingCarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
@RestController
@Api(tags = "购物车")
@AllArgsConstructor
@RequestMapping("/webapp/shopping/car")
public class ShoppingCarController {

    private final ShoppingCarService shoppingCarService;

    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public RespBody<Void> add(@RequestBody @Validated AddCarDTO dto) {
        shoppingCarService.add(dto);
        return RespBody.success();
    }

    @GetMapping("/list")
    @ApiOperation("购物车商品列表")
    public List<ShoppingCarVO> list() {
        return shoppingCarService.getList(ApiHolder.getUserId());
    }

    @GetMapping("/delete")
    @ApiOperation("删除购物车商品")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        shoppingCarService.delete(dto.getId(), ApiHolder.getUserId());
        return RespBody.success();
    }

    @PostMapping("/quantity")
    @ApiOperation("更新商品数量")
    public RespBody<Void> quantity(@RequestBody @Validated CarQuantityDTO dto) {
        shoppingCarService.updateQuantity(dto.getId(), dto.getQuantity(), ApiHolder.getUserId());
        return RespBody.success();
    }

}
