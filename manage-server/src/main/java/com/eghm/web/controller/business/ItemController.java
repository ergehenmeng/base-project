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
import com.eghm.enums.ref.State;
import com.eghm.service.business.ItemService;
import com.eghm.utils.ExcelUtil;
import com.eghm.vo.business.item.ActivityItemResponse;
import com.eghm.vo.business.item.ItemDetailResponse;
import com.eghm.vo.business.item.ItemResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */

@RestController
@Api(tags = "商品管理")
@AllArgsConstructor
@RequestMapping("/manage/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/listPage")
    @ApiOperation("商品列表")
    public RespBody<PageData<ItemResponse>> listPage(ItemQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<ItemResponse> byPage = itemService.getByPage(request);
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
    public RespBody<ItemDetailResponse> select(@Validated IdDTO dto) {
        ItemDetailResponse detail = itemService.getDetailById(dto.getId());
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

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        itemService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
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

    @PostMapping("/activityList")
    @ApiOperation("活动商品列表")
    public RespBody<List<ActivityItemResponse>> activityList(@RequestBody @Validated IdDTO dto) {
        List<ActivityItemResponse> activityList = itemService.getActivityList(SecurityHolder.getMerchantId(), dto.getId());
        return RespBody.success(activityList);
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, ItemQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<ItemResponse> byPage = itemService.getList(request);
        ExcelUtil.export(response, "零售信息", byPage, ItemResponse.class);
    }
}
