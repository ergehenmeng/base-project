package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.business.item.ItemAddRequest;
import com.eghm.dto.business.item.ItemEditRequest;
import com.eghm.dto.business.item.ItemQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import com.eghm.service.business.ItemService;
import com.eghm.vo.business.item.ItemListResponse;
import com.eghm.vo.business.item.ItemResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */

@RestController
@Api(tags = "商品管理")
@AllArgsConstructor
@RequestMapping("/manage/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/listPage")
    @ApiOperation("商品列表")
    public RespBody<PageData<ItemListResponse>> listPage(ItemQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<ItemListResponse> byPage = itemService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping("/create")
    @ApiOperation("新增商品")
    public RespBody<Void> create(@Validated @RequestBody ItemAddRequest request) {
        itemService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新商品")
    public RespBody<Void> update(@Validated @RequestBody ItemEditRequest request) {
        itemService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询商品")
    public RespBody<ItemResponse> select(@Validated IdDTO dto) {
        ItemResponse detail = itemService.getDetailById(dto.getId());
        return RespBody.success(detail);
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        itemService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        itemService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformAudit")
    @ApiOperation("平台上架审核")
    public RespBody<Void> platformAudit(@RequestBody @Validated IdDTO dto) {
        itemService.updateAuditState(dto.getId(), PlatformState.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        itemService.updateAuditState(dto.getId(), PlatformState.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/recommend")
    @ApiOperation("设置推荐商品")
    public RespBody<Void> recommend(@RequestBody @Validated IdDTO dto) {
        itemService.setRecommend(dto.getId());
        return RespBody.success();
    }

    @PostMapping("/sortBy")
    @ApiOperation("商品排序设置")
    public RespBody<Void> recommend(@RequestBody @Validated SortByDTO dto) {
        itemService.sortBy(dto.getId(), dto.getSortBy());
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        itemService.deleteById(dto.getId());
        return RespBody.success();
    }
}
