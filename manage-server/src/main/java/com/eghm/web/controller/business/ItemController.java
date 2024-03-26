package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.item.ItemAddRequest;
import com.eghm.dto.business.item.ItemEditRequest;
import com.eghm.dto.business.item.ItemQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.State;
import com.eghm.service.business.ItemService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.item.ActivityItemResponse;
import com.eghm.vo.business.item.ItemDetailResponse;
import com.eghm.vo.business.item.ItemResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */

@RestController
@Api(tags = "零售管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<ItemResponse>> listPage(ItemQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<ItemResponse> byPage = itemService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/productListPage")
    @ApiOperation("列表含店铺信息")
    public RespBody<PageData<BaseProductResponse>> productListPage(BaseProductQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseProductResponse> listPage = itemService.getProductPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody ItemAddRequest request) {
        itemService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody ItemEditRequest request) {
        itemService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<ItemDetailResponse> select(@Validated IdDTO dto) {
        ItemDetailResponse detail = itemService.getDetailById(dto.getId());
        return RespBody.success(detail);
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        itemService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        itemService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        itemService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/recommend", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("设置推荐商品")
    public RespBody<Void> recommend(@RequestBody @Validated IdDTO dto) {
        itemService.setRecommend(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("排序")
    public RespBody<Void> sort(@RequestBody @Validated SortByDTO dto) {
        itemService.sortBy(dto.getId(), dto.getSortBy());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        itemService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/activityList")
    @ApiOperation("未参加活动的商品列表")
    public RespBody<List<ActivityItemResponse>> activityList(IdDTO dto) {
        List<ActivityItemResponse> activityList = itemService.getActivityList(SecurityHolder.getMerchantId(), dto.getId());
        return RespBody.success(activityList);
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, ItemQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<ItemResponse> byPage = itemService.getList(request);
        EasyExcelUtil.export(response, "零售信息", byPage, ItemResponse.class);
    }

}
