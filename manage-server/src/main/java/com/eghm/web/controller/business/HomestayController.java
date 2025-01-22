package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.homestay.HomestayAddRequest;
import com.eghm.dto.business.homestay.HomestayEditRequest;
import com.eghm.dto.business.homestay.HomestayQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.State;
import com.eghm.model.Homestay;
import com.eghm.service.business.HomestayService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.homestay.BaseHomestayResponse;
import com.eghm.vo.business.homestay.HomestayDetailResponse;
import com.eghm.vo.business.homestay.HomestayResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 */
@RestController
@Tag(name="民宿管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/homestay", produces = MediaType.APPLICATION_JSON_VALUE)
public class HomestayController {

    private final HomestayService homestayService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<HomestayResponse>> listPage(HomestayQueryRequest request) {
        SecurityHolder.getMerchantOptional().ifPresent(request::setMerchantId);
        Page<HomestayResponse> byPage = homestayService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/list")
    @Operation(summary = "列表")
    public RespBody<List<BaseHomestayResponse>> list() {
        List<BaseHomestayResponse> responseList = homestayService.getList();
        return RespBody.success(responseList);
    }

    @GetMapping("/storeListPage")
    @Operation(summary = "列表含商户信息")
    public RespBody<PageData<BaseStoreResponse>> storeListPage(BaseStoreQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseStoreResponse> listPage = homestayService.getStorePage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@RequestBody @Validated HomestayAddRequest request) {
        homestayService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@RequestBody @Validated HomestayEditRequest request) {
        homestayService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        homestayService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        homestayService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        homestayService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        homestayService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<HomestayDetailResponse> select(@Validated IdDTO dto) {
        Homestay homestay = homestayService.selectByIdRequired(dto.getId());
        return RespBody.success(DataUtil.copy(homestay, HomestayDetailResponse.class));
    }

    @GetMapping("/export")
    @Operation(summary = "民宿导出")
    public void export(HttpServletResponse response, HomestayQueryRequest request) {
        SecurityHolder.getMerchantOptional().ifPresent(request::setMerchantId);
        List<HomestayResponse> byPage = homestayService.getList(request);
        EasyExcelUtil.export(response, "民宿信息", byPage, HomestayResponse.class);
    }

}
