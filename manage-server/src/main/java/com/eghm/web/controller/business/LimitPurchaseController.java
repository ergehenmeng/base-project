package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.purchase.LimitPurchaseQueryRequest;
import com.eghm.dto.business.purchase.LimitPurchaseAddRequest;
import com.eghm.dto.business.purchase.LimitPurchaseEditRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.LimitPurchaseService;
import com.eghm.vo.business.limit.LimitPurchaseDetailResponse;
import com.eghm.vo.business.limit.LimitPurchaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2024/1/27
 */

@RestController
@Api(tags = "限时购")
@AllArgsConstructor
@RequestMapping("/manage/limit/purchase")
public class LimitPurchaseController {

    private final LimitPurchaseService limitPurchaseService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<LimitPurchaseResponse>> listPage(LimitPurchaseQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<LimitPurchaseResponse> byPage = limitPurchaseService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("创建")
    public RespBody<Void> create(@RequestBody @Validated LimitPurchaseAddRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        limitPurchaseService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新")
    public RespBody<Void> update(@RequestBody @Validated LimitPurchaseEditRequest request) {
        limitPurchaseService.update(request);
        return RespBody.success();
    }

    @PostMapping("/detail")
    @ApiOperation("详情")
    public RespBody<LimitPurchaseDetailResponse> detail(@RequestBody @Validated IdDTO dto) {
        LimitPurchaseDetailResponse response = limitPurchaseService.detailById(dto.getId());
        return RespBody.success(response);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        limitPurchaseService.delete(dto.getId());
        return RespBody.success();
    }

}
