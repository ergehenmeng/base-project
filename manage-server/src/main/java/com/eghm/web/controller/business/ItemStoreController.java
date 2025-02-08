package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.RecommendDTO;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.item.store.ItemStoreAddRequest;
import com.eghm.dto.business.item.store.ItemStoreEditRequest;
import com.eghm.dto.business.item.store.ItemStoreQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.State;
import com.eghm.model.ItemStore;
import com.eghm.service.business.ItemStoreService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.item.store.BaseItemStoreResponse;
import com.eghm.vo.business.item.store.ItemStoreResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/22
 */
@RestController
@Tag(name="店铺管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/item/store", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemStoreController {

    private final ItemStoreService itemStoreService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<ItemStoreResponse>> listPage(@ParameterObject ItemStoreQueryRequest request) {
        SecurityHolder.getMerchantOptional().ifPresent(request::setMerchantId);
        Page<ItemStoreResponse> byPage = itemStoreService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/storeListPage")
    @Operation(summary = "列表含店铺信息")
    public RespBody<PageData<BaseStoreResponse>> storeListPage(@ParameterObject BaseStoreQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseStoreResponse> listPage = itemStoreService.getStorePage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @GetMapping("/list")
    @Operation(summary = "列表(不分页)")
    public RespBody<List<BaseItemStoreResponse>> list() {
        List<BaseItemStoreResponse> responseList = itemStoreService.getList(SecurityHolder.getMerchantId());
        return RespBody.success(responseList);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody ItemStoreAddRequest request) {
        itemStoreService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody ItemStoreEditRequest request) {
        itemStoreService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<ItemStore> select(@ParameterObject @Validated IdDTO dto) {
        ItemStore store = itemStoreService.selectByIdRequired(dto.getId());
        return RespBody.success(store);
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        itemStoreService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        itemStoreService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        itemStoreService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/recommend", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "设置为推荐店铺")
    public RespBody<Void> recommend(@RequestBody @Validated RecommendDTO dto) {
        itemStoreService.setRecommend(dto.getId(), dto.getRecommend());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        itemStoreService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @Operation(summary = "导出")
    public void export(HttpServletResponse response, ItemStoreQueryRequest request) {
        SecurityHolder.getMerchantOptional().ifPresent(request::setMerchantId);
        List<ItemStoreResponse> byPage = itemStoreService.getList(request);
        EasyExcelUtil.export(response, "零售店铺信息", byPage, ItemStoreResponse.class);
    }
}
