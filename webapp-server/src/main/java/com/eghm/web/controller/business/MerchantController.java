package com.eghm.web.controller.business;

import com.eghm.dto.business.merchant.MerchantAuthDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.MerchantService;
import com.eghm.vo.business.merchant.MerchantAuthVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛 2022/6/24 15:13
 */
@RestController
@Tag(name="绑定微信")
@AllArgsConstructor
@RequestMapping(value = "/webapp/merchant", produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantController {

    private final MerchantService merchantService;

    /**
     * 用户扫码后展示即将绑定的商户信息及手机号信息,绑定后再次扫码依旧会显示之前的手机号(除非解绑)
     */
    @GetMapping("/authMobile")
    @Operation(summary = "微信授权获取手机号")
    @Parameter(name = "jsCode", description = "手机号授权code", required = true)
    @Parameter(name = "authCode", description = "授权码", required = true)
    public RespBody<MerchantAuthVO> authMobile(@RequestParam("jsCode") String jsCode, @RequestParam("authCode") String authCode) {
        MerchantAuthVO vo = merchantService.getAuth(jsCode, authCode);
        return RespBody.success(vo);
    }

    /**
     * 确认绑定
     */
    @PostMapping(value = "/binding", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "绑定")
    public RespBody<Void> binding(@RequestBody @Validated MerchantAuthDTO dto) {
        merchantService.binding(dto);
        return RespBody.success();
    }

}
