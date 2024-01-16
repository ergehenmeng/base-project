package com.eghm.web.controller.business;

import com.eghm.dto.business.merchant.MerchantAuthDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛 2022/6/24 15:13
 */
@RestController
@Api(tags = "绑定微信")
@AllArgsConstructor
@RequestMapping("/webapp/merchant")
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping("/binding")
    @ApiOperation("绑定")
    public RespBody<Void> binding(@RequestBody @Validated MerchantAuthDTO dto) {
        merchantService.binding(dto);
        return RespBody.success();
    }

}
