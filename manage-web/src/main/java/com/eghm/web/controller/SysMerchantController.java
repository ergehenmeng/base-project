package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.SysMerchant;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.merchant.MerchantQueryRequest;
import com.eghm.service.business.SysMerchantService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyb 2022/6/24 15:13
 */
@RestController
@Api(tags = "商户管理")
@AllArgsConstructor
@RequestMapping("/merchant")
public class SysMerchantController {

    private final SysMerchantService sysMerchantService;


    @GetMapping("/listPage")
    public RespBody<PageData<SysMerchant>> listPage(MerchantQueryRequest request) {
        Page<SysMerchant> merchantPage = sysMerchantService.getByPage(request);
        return RespBody.success(PageData.toPage(merchantPage));
    }

}
