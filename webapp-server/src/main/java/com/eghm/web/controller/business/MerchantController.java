package com.eghm.web.controller.business;

import com.eghm.dto.business.merchant.MerchantAuthDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.MerchantService;
import com.eghm.vo.business.merchant.MerchantAuthVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛 2022/6/24 15:13
 */
@RestController
@Api(tags = "绑定微信")
@AllArgsConstructor
@RequestMapping("/webapp/merchant")
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/authMobile")
    @ApiOperation("微信授权获取手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jsCode", value = "手机号授权code", required = true),
            @ApiImplicitParam(name = "authCode", value = "授权码", required = true),
    })
    public RespBody<MerchantAuthVO> authMobile(@RequestParam("jsCode") String jsCode, @RequestParam("authCode") String authCode) {
        MerchantAuthVO vo = merchantService.getAuth(jsCode, authCode);
        return RespBody.success(vo);
    }

    @PostMapping("/binding")
    @ApiOperation("绑定")
    public RespBody<Void> binding(@RequestBody @Validated MerchantAuthDTO dto) {
        merchantService.binding(dto);
        return RespBody.success();
    }

}
