package com.eghm.web.controller.operate;

import com.eghm.cache.ClearCacheService;
import com.eghm.dto.business.pay.PayConfigEditRequest;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.PayConfig;
import com.eghm.service.business.PayConfigService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author 二哥很猛
* @since 2024-04-15
*/
@RestController
@Tag(name="支付管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/pay/config", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayConfigController {

    private final PayConfigService payConfigService;

    private final ClearCacheService clearCacheService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<List<PayConfig>> listPage(@ParameterObject PagingQuery request) {
        List<PayConfig> byPage = payConfigService.getList(request.getQueryName());
        return RespBody.success(byPage);
    }

    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody PayConfigEditRequest request) {
        payConfigService.update(request);
        clearCacheService.clearPayConfig();
        return RespBody.success();
    }
}