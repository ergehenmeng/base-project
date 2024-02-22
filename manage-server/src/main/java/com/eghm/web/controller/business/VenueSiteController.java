package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.venue.VenueSiteAddRequest;
import com.eghm.dto.business.venue.VenueSiteEditRequest;
import com.eghm.dto.business.venue.VenueSiteQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.State;
import com.eghm.model.VenueSite;
import com.eghm.service.business.VenueSiteService;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.venue.VenueSiteResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@RestController
@Api(tags = "场馆场地")
@AllArgsConstructor
@RequestMapping("/manage/venue/site")
public class VenueSiteController {

    private final VenueSiteService venueSiteService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<VenueSiteResponse>> listPage(@Validated VenueSiteQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<VenueSiteResponse> byPage = venueSiteService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/productListPage")
    @ApiOperation("列表含店铺信息")
    public RespBody<PageData<BaseProductResponse>> productListPage(BaseProductQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseProductResponse> listPage = venueSiteService.getProductPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping("/create")
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody VenueSiteAddRequest request) {
        venueSiteService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody VenueSiteEditRequest request) {
        venueSiteService.update(request);
        return RespBody.success();
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        venueSiteService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        venueSiteService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        venueSiteService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/sort")
    @ApiOperation("排序")
    public RespBody<Void> sort(@RequestBody @Validated SortByDTO dto) {
        venueSiteService.sortBy(dto.getId(), dto.getSortBy());
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<VenueSite> select(@Validated IdDTO dto) {
        VenueSite venueSite = venueSiteService.selectByIdRequired(dto.getId());
        return RespBody.success(venueSite);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        venueSiteService.delete(dto.getId());
        return RespBody.success();
    }
}
