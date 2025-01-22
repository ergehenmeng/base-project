package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.venue.VenueSiteAddRequest;
import com.eghm.dto.business.venue.VenueSiteEditRequest;
import com.eghm.dto.business.venue.VenueSitePriceQueryRequest;
import com.eghm.dto.business.venue.VenueSiteQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.State;
import com.eghm.service.business.VenueSitePriceService;
import com.eghm.service.business.VenueSiteService;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.venue.VenueSiteDetailResponse;
import com.eghm.vo.business.venue.VenueSitePriceVO;
import com.eghm.vo.business.venue.VenueSiteResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@RestController
@Tag(name="场馆场地")
@AllArgsConstructor
@RequestMapping(value = "/manage/venue/site", produces = MediaType.APPLICATION_JSON_VALUE)
public class VenueSiteController {

    private final VenueSiteService venueSiteService;

    private final VenueSitePriceService venueSitePriceService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<VenueSiteResponse>> listPage(@Validated VenueSiteQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<VenueSiteResponse> byPage = venueSiteService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/productListPage")
    @Operation(summary = "列表(含店铺)")
    public RespBody<PageData<BaseProductResponse>> productListPage(BaseProductQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseProductResponse> listPage = venueSiteService.getProductPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody VenueSiteAddRequest request) {
        venueSiteService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新")
    public RespBody<Void> update(@Validated @RequestBody VenueSiteEditRequest request) {
        venueSiteService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        venueSiteService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        venueSiteService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        venueSiteService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "排序")
    public RespBody<Void> sort(@RequestBody @Validated SortByDTO dto) {
        venueSiteService.sortBy(dto.getId(), dto.getSortBy());
        return RespBody.success();
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<VenueSiteDetailResponse> select(@Validated IdDTO dto) {
        VenueSiteDetailResponse response = venueSiteService.getDetail(dto.getId());
        return RespBody.success(response);
    }

    @GetMapping("/priceList")
    @Operation(summary = "场地价格信息")
    public RespBody<List<VenueSitePriceVO>> priceList(@Validated VenueSitePriceQueryRequest request) {
        List<VenueSitePriceVO> priceList = venueSitePriceService.getPriceList(request.getVenueSiteId(), request.getNowDate());
        return RespBody.success(priceList);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        venueSiteService.delete(dto.getId());
        return RespBody.success();
    }
}
