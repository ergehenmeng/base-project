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
import com.eghm.utils.DataUtil;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.line.BaseTravelResponse;
import com.eghm.vo.business.line.TravelDetailResponse;
import com.eghm.vo.business.line.TravelResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 殿小二
 * @since 2023/2/22
 */
@RestController
@Tag(name="旅行社")
@AllArgsConstructor
@RequestMapping(value = "/manage/travel", produces = MediaType.APPLICATION_JSON_VALUE)
public class TravelAgencyController {

    private final TravelAgencyService travelAgencyService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<TravelResponse>> listPage(TravelAgencyQueryRequest request) {
        // 默认查询当前商户, 如果是管理员则可以查询所有商户,因此商户ID支持前端传递
        SecurityHolder.getMerchantOptional().ifPresent(request::setMerchantId);
        Page<TravelResponse> roomPage = travelAgencyService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @GetMapping("/storeListPage")
    @Operation(summary = "列表含商户信息")
    public RespBody<PageData<BaseStoreResponse>> storeListPage(BaseStoreQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseStoreResponse> listPage = travelAgencyService.getStorePage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @GetMapping("/list")
    @Operation(summary = "列表")
    public RespBody<List<BaseTravelResponse>> list() {
        List<BaseTravelResponse> list = travelAgencyService.getList(SecurityHolder.getMerchantId());
        return RespBody.success(list);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody TravelAgencyAddRequest request) {
        travelAgencyService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新")
    public RespBody<Void> update(@Validated @RequestBody TravelAgencyEditRequest request) {
        travelAgencyService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        travelAgencyService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        travelAgencyService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        travelAgencyService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<TravelDetailResponse> select(@Validated IdDTO dto) {
        TravelAgency travelAgency = travelAgencyService.selectByIdRequired(dto.getId());
        return RespBody.success(DataUtil.copy(travelAgency, TravelDetailResponse.class));
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        travelAgencyService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @Operation(summary = "导出")
    public void export(HttpServletResponse response, TravelAgencyQueryRequest request) {
        SecurityHolder.getMerchantOptional().ifPresent(request::setMerchantId);
        List<TravelResponse> byPage = travelAgencyService.getList(request);
        EasyExcelUtil.export(response, "旅行社列表", byPage, TravelResponse.class);
    }
}
