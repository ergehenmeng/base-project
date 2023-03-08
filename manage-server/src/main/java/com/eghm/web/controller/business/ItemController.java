package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.SortByDTO;
import com.eghm.model.dto.business.item.ItemAddRequest;
import com.eghm.model.dto.business.item.ItemEditRequest;
import com.eghm.model.dto.business.item.ItemQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.vo.business.item.ItemListResponse;
import com.eghm.model.vo.business.item.ItemResponse;
import com.eghm.service.business.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public PageData<ItemListResponse> listPage(ItemQueryRequest request) {
        Page<ItemListResponse> byPage = itemService.getByPage(request);
        return PageData.toPage(byPage);
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
    @ApiImplicitParam(name = "id", value = "商品id", required = true)
    public ItemResponse select(@RequestParam("id") Long id) {
        return itemService.getDetailById(id);
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
    public RespBody<Void> updateState(@RequestBody @Validated IdDTO dto) {
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
}
