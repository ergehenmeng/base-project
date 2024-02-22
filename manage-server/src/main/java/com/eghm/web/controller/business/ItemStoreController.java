package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.item.store.ItemStoreAddRequest;
import com.eghm.dto.business.item.store.ItemStoreEditRequest;
import com.eghm.dto.business.item.store.ItemStoreQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.State;
import com.eghm.model.ItemStore;
import com.eghm.service.business.ItemStoreService;
import com.eghm.vo.business.base.BaseStoreResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2022/7/22
 */
@RestController
@Api(tags = "店铺管理")
@AllArgsConstructor
@RequestMapping("/manage/item/store")
public class ItemStoreController {

    private final ItemStoreService itemStoreService;

    @GetMapping("/listPage")
    @ApiOperation("店铺列表")
    public RespBody<PageData<ItemStore>> listPage(ItemStoreQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<ItemStore> byPage = itemStoreService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/storeListPage")
    @ApiOperation("列表含商户信息")
    public RespBody<PageData<BaseStoreResponse>> storeListPage(BaseStoreQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseStoreResponse> listPage = itemStoreService.getStorePage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping("/create")
    @ApiOperation("新增店铺")
    public RespBody<Void> create(@Validated @RequestBody ItemStoreAddRequest request) {
        itemStoreService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新店铺")
    public RespBody<Void> update(@Validated @RequestBody ItemStoreEditRequest request) {
        itemStoreService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询店铺")
    public RespBody<ItemStore> select(@Validated IdDTO dto) {
        ItemStore store = itemStoreService.selectByIdRequired(dto.getId());
        return RespBody.success(store);
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        itemStoreService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        itemStoreService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        itemStoreService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/recommend")
    @ApiOperation("设置为推荐店铺")
    public RespBody<Void> recommend(@RequestBody @Validated IdDTO dto) {
        itemStoreService.setRecommend(dto.getId());
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        itemStoreService.deleteById(dto.getId());
        return RespBody.success();
    }
}
