package com.eghm.web.controller;

import com.eghm.cache.ClearCacheService;
import com.eghm.dto.business.pay.PayConfigEditRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.PayConfig;
import com.eghm.service.business.PayConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author 二哥很猛
* @since 2024-04-15
*/
@RestController
@Api(tags = "支付管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/pay/config", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayConfigController {

    private final PayConfigService payConfigService;

    private final ClearCacheService clearCacheService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<List<PayConfig>> listPage() {
        List<PayConfig> byPage = payConfigService.getList();
        return RespBody.success(byPage);
    }

    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody PayConfigEditRequest request) {
        payConfigService.update(request);
        clearCacheService.clearPayConfig();
        return RespBody.success();
    }
}