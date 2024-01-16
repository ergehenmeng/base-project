package com.eghm.web.controller.business;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.merchant.MerchantAuthDTO;
import com.eghm.dto.business.merchant.MerchantUnbindDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.MerchantService;
import com.eghm.utils.IpUtil;
import com.eghm.vo.business.merchant.MerchantAuthVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 商家自己查看自己的信息
 * @author 二哥很猛 2024/1/21
 */
@RestController
@Api(tags = "商家中心")
@AllArgsConstructor
@RequestMapping("/manage/merchant/center")
public class MerchantCenterController {

    private final MerchantService merchantService;

    @PostMapping("/sendSms")
    @ApiOperation("发送解绑短信")
    public RespBody<Void> sendSms(HttpServletRequest request) {
        merchantService.sendUnbindSms(SecurityHolder.getMerchantId(), IpUtil.getIpAddress(request));
        return RespBody.success();
    }

    @PostMapping("/unbind")
    @ApiOperation("解绑")
    public RespBody<Void> unbind(@RequestBody @Validated MerchantUnbindDTO dto) {
        merchantService.unbind(SecurityHolder.getMerchantId(), dto.getSmsCode());
        return RespBody.success();
    }

    @PostMapping("/binding")
    @ApiOperation("绑定")
    public RespBody<Void> binding(@RequestBody @Validated MerchantAuthDTO dto) {
        merchantService.binding(dto);
        return RespBody.success();
    }

    @PostMapping("/generate")
    @ApiOperation("生成二维码")
    public RespBody<MerchantAuthVO> generate() {
        MerchantAuthVO vo = merchantService.generateAuthCode(SecurityHolder.getMerchantId());
        return RespBody.success(vo);
    }
}
