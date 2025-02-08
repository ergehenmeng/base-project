package com.eghm.web.controller.business;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.merchant.address.AddressAddRequest;
import com.eghm.dto.business.merchant.address.AddressEditRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.MerchantAddress;
import com.eghm.service.business.MerchantAddressService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.merchant.address.MerchantAddressDetailResponse;
import com.eghm.vo.business.merchant.address.MerchantAddressResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 殿小二
 * @since 2024/3/18
 */
@RestController
@Tag(name="商户收货地址")
@AllArgsConstructor
@RequestMapping(value = "/manage/merchant/address", produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantAddressController {

    private final MerchantAddressService merchantAddressService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<MerchantAddressResponse>> list(PagingQuery query) {
        PageData<MerchantAddressResponse> byPage = merchantAddressService.getByPage(query);
        return RespBody.success(byPage);
    }

    @GetMapping("/list")
    @Operation(summary = "列表(不分页)")
    @Parameter(name = "merchantId", description = "商户id", required = true)
    public RespBody<List<MerchantAddressResponse>> list(@RequestParam("merchantId") Long merchantId) {
        if (merchantId == null) {
            merchantId = SecurityHolder.getMerchantId();
        }
        List<MerchantAddressResponse> voList = merchantAddressService.getList(merchantId);
        return RespBody.success(voList);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "添加")
    public RespBody<Void> create(@RequestBody @Validated AddressAddRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        merchantAddressService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@RequestBody @Validated AddressEditRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        merchantAddressService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        merchantAddressService.delete(dto.getId());
        return RespBody.success();
    }

    @GetMapping(value = "/select")
    @Operation(summary = "查询")
    public RespBody<MerchantAddressDetailResponse> select(@Validated IdDTO dto) {
        MerchantAddress address = merchantAddressService.selectByIdRequired(dto.getId());
        return RespBody.success(DataUtil.copy(address, MerchantAddressDetailResponse.class));
    }
}
