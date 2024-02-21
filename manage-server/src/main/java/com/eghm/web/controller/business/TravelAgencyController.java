package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.travel.TravelAgencyAddRequest;
import com.eghm.dto.business.travel.TravelAgencyEditRequest;
import com.eghm.dto.business.travel.TravelAgencyQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.State;
import com.eghm.model.TravelAgency;
import com.eghm.service.business.TravelAgencyService;
import com.eghm.vo.business.base.BaseStoreResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 殿小二
 * @since 2023/2/22
 */
@RestController
@Api(tags = "旅行社")
@AllArgsConstructor
@RequestMapping("/manage/travel")
public class TravelAgencyController {

    private final TravelAgencyService travelAgencyService;

    @GetMapping("/listPage")
    @ApiOperation("旅行社列表")
    public RespBody<PageData<TravelAgency>> listPage(TravelAgencyQueryRequest request) {
        Page<TravelAgency> roomPage = travelAgencyService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @GetMapping("/storeListPage")
    @ApiOperation("列表含商户信息")
    public RespBody<PageData<BaseStoreResponse>> storeListPage(BaseStoreQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseStoreResponse> listPage = travelAgencyService.getStorePage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping("/create")
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody TravelAgencyAddRequest request) {
        travelAgencyService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody TravelAgencyEditRequest request) {
        travelAgencyService.update(request);
        return RespBody.success();
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        travelAgencyService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        travelAgencyService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        travelAgencyService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<TravelAgency> select(@Validated IdDTO dto) {
        TravelAgency travelAgency = travelAgencyService.selectByIdRequired(dto.getId());
        return RespBody.success(travelAgency);
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        travelAgencyService.deleteById(dto.getId());
        return RespBody.success();
    }
}
