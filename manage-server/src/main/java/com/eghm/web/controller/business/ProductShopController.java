package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.ProductShop;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.product.shop.ProductShopAddRequest;
import com.eghm.model.dto.business.product.shop.ProductShopEditRequest;
import com.eghm.model.dto.business.product.shop.ProductShopQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.business.ProductShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2022/7/22
 */
@RestController
@Api(tags = "店铺管理")
@AllArgsConstructor
@RequestMapping("/manage/product/shop")
public class ProductShopController {

    private final ProductShopService productShopService;

    @GetMapping("/listPage")
    @ApiOperation("店铺列表")
    public PageData<ProductShop> listPage(ProductShopQueryRequest request) {
        Page<ProductShop> byPage = productShopService.getByPage(request);
        return PageData.toPage(byPage);
    }

    @PostMapping("/create")
    @ApiOperation("新增店铺")
    public RespBody<Void> create(@Validated @RequestBody ProductShopAddRequest request) {
        productShopService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新店铺")
    public RespBody<Void> update(@Validated @RequestBody ProductShopEditRequest request) {
        productShopService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询店铺")
    @ApiImplicitParam(name = "id", value = "店铺id", required = true)
    public ProductShop select(@RequestParam("id") Long id) {
        return productShopService.selectById(id);
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        productShopService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        productShopService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformAudit")
    @ApiOperation("平台上架审核")
    public RespBody<Void> updateState(@RequestBody @Validated IdDTO dto) {
        productShopService.updateAuditState(dto.getId(), PlatformState.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        productShopService.updateAuditState(dto.getId(), PlatformState.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/recommend")
    @ApiOperation("设置为推荐店铺")
    public RespBody<Void> recommend(@RequestBody @Validated IdDTO dto) {
        productShopService.setRecommend(dto.getId());
        return RespBody.success();
    }

}
