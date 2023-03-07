package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.Product;
import com.eghm.model.ProductSku;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.SortByDTO;
import com.eghm.model.dto.business.product.ProductAddRequest;
import com.eghm.model.dto.business.product.ProductEditRequest;
import com.eghm.model.dto.business.product.ItemQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.business.product.ProductListResponse;
import com.eghm.model.vo.business.product.ProductResponse;
import com.eghm.model.vo.business.product.ProductSkuResponse;
import com.eghm.service.business.ProductService;
import com.eghm.service.business.ProductSkuService;
import com.eghm.utils.DataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@Api(tags = "商品管理")
@AllArgsConstructor
@RequestMapping("/manage/product")
public class ProductController {

    private final ProductService productService;

    private final ProductSkuService productSkuService;

    @GetMapping("/listPage")
    @ApiOperation("商品列表")
    public PageData<ProductListResponse> listPage(ItemQueryRequest request) {
        Page<ProductListResponse> byPage = productService.getByPage(request);
        return PageData.toPage(byPage);
    }

    @PostMapping("/create")
    @ApiOperation("新增商品")
    public RespBody<Void> create(@Validated @RequestBody ProductAddRequest request) {
        productService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新商品")
    public RespBody<Void> update(@Validated @RequestBody ProductEditRequest request) {
        productService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询商品")
    @ApiImplicitParam(name = "id", value = "商品id", required = true)
    public ProductResponse select(@RequestParam("id") Long id) {
        Product product = productService.selectById(id);
        ProductResponse response = DataUtil.copy(product, ProductResponse.class);
        List<ProductSku> skuList = productSkuService.selectByProductId(id);
        response.setSkuList(DataUtil.copy(skuList, ProductSkuResponse.class));
        response.setVirtualNum(product.getTotalNum() - product.getSaleNum());
        return response;
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        productService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        productService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformAudit")
    @ApiOperation("平台上架审核")
    public RespBody<Void> updateState(@RequestBody @Validated IdDTO dto) {
        productService.updateAuditState(dto.getId(), PlatformState.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        productService.updateAuditState(dto.getId(), PlatformState.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/recommend")
    @ApiOperation("设置推荐商品")
    public RespBody<Void> recommend(@RequestBody @Validated IdDTO dto) {
        productService.setRecommend(dto.getId());
        return RespBody.success();
    }

    @PostMapping("/sortBy")
    @ApiOperation("商品排序设置")
    public RespBody<Void> recommend(@RequestBody @Validated SortByDTO dto) {
        productService.sortBy(dto.getId(), dto.getSortBy());
        return RespBody.success();
    }
}
