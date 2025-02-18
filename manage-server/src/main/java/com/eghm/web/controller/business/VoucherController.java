package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.restaurant.voucher.VoucherAddRequest;
import com.eghm.dto.business.restaurant.voucher.VoucherEditRequest;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.State;
import com.eghm.model.Voucher;
import com.eghm.service.business.VoucherService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.restaurant.VoucherDetailResponse;
import com.eghm.vo.business.restaurant.VoucherResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/30
 */
@RestController
@Tag(name="餐饮券")
@AllArgsConstructor
@RequestMapping(value = "/manage/restaurant/voucher", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoucherController {

    private final VoucherService voucherService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<VoucherResponse>> listPage(@ParameterObject VoucherQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<VoucherResponse> roomPage = voucherService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @GetMapping("/productListPage")
    @Operation(summary = "列表(含店铺)")
    public RespBody<PageData<BaseProductResponse>> productListPage(@ParameterObject BaseProductQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseProductResponse> listPage = voucherService.getProductPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody VoucherAddRequest request) {
        voucherService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新")
    public RespBody<Void> update(@Validated @RequestBody VoucherEditRequest request) {
        voucherService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        voucherService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        voucherService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        voucherService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<VoucherDetailResponse> select(@Validated IdDTO dto) {
        Voucher voucher = voucherService.selectByIdRequired(dto.getId());
        VoucherDetailResponse response = DataUtil.copy(voucher, VoucherDetailResponse.class);
        response.setVirtualNum(voucher.getTotalNum() - voucher.getSaleNum());
        return RespBody.success(response);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        voucherService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @Operation(summary = "导出")
    public void export(HttpServletResponse response, VoucherQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<VoucherResponse> byPage = voucherService.getList(request);
        EasyExcelUtil.export(response, "餐饮券", byPage, VoucherResponse.class);
    }

}
