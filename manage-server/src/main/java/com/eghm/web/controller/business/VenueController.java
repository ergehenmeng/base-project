package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.venue.VenueAddRequest;
import com.eghm.dto.business.venue.VenueEditRequest;
import com.eghm.dto.business.venue.VenueQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.State;
import com.eghm.model.Venue;
import com.eghm.service.business.VenueService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.venue.BaseVenueResponse;
import com.eghm.vo.business.venue.VenueDetailResponse;
import com.eghm.vo.business.venue.VenueResponse;
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
 * @since 2024/1/31
 */

@RestController
@Api(tags = "场馆")
@AllArgsConstructor
@RequestMapping(value = "/manage/venue", produces = MediaType.APPLICATION_JSON_VALUE)
public class VenueController {

    private final VenueService venueService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<VenueResponse>> listPage(VenueQueryRequest request) {
        SecurityHolder.getMerchantOptional().ifPresent(request::setMerchantId);
        Page<VenueResponse> byPage = venueService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/storeListPage")
    @ApiOperation("列表含商户信息")
    public RespBody<PageData<BaseStoreResponse>> storeListPage(BaseStoreQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseStoreResponse> listPage = venueService.getStorePage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @GetMapping("/list")
    @ApiOperation("场馆列表")
    public RespBody<List<BaseVenueResponse>> list() {
        List<BaseVenueResponse> list = venueService.getList(SecurityHolder.getMerchantId());
        return RespBody.success(list);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody VenueAddRequest request) {
        venueService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody VenueEditRequest request) {
        venueService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        venueService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        venueService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        venueService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<VenueDetailResponse> select(@Validated IdDTO dto) {
        Venue venue = venueService.selectByIdRequired(dto.getId());
        return RespBody.success(DataUtil.copy(venue, VenueDetailResponse.class));
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        venueService.delete(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, VenueQueryRequest request) {
        SecurityHolder.getMerchantOptional().ifPresent(request::setMerchantId);
        List<VenueResponse> byPage = venueService.getList(request);
        EasyExcelUtil.export(response, "场馆信息", byPage, VenueResponse.class);
    }
}
