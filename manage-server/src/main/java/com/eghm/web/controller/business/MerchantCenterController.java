package com.eghm.web.controller.business;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.merchant.MerchantUnbindDTO;
import com.eghm.dto.business.merchant.MerchantWithdrawChangeRequest;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.MerchantType;
import com.eghm.model.Merchant;
import com.eghm.service.business.MerchantService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.IpUtil;
import com.eghm.vo.business.merchant.MerchantAuthResponse;
import com.eghm.vo.business.merchant.MerchantDetailResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 商家自己查看自己的信息
 *
 * @author 二哥很猛 2024/1/21
 */
@RestController
@Api(tags = "商家中心")
@AllArgsConstructor
@RequestMapping(value = "/manage/merchant/center", produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantCenterController {

    private final SysAreaService sysAreaService;

    private final MerchantService merchantService;

    @GetMapping("/detail")
    @ApiOperation("详情")
    public RespBody<MerchantDetailResponse> detail() {
        Merchant merchant = merchantService.selectByIdRequired(SecurityHolder.getMerchantId());
        MerchantDetailResponse response = DataUtil.copy(merchant, MerchantDetailResponse.class);
        response.setDetailAddress(sysAreaService.parseArea(merchant.getCityId(), merchant.getCountyId(), merchant.getDetailAddress()));
        response.setTypeList(MerchantType.parse(merchant.getType()));
        return RespBody.success(response);
    }

    @PostMapping(value = "/sendSms")
    @ApiOperation("发送解绑短信")
    public RespBody<Void> sendSms(HttpServletRequest request) {
        merchantService.sendUnbindSms(SecurityHolder.getMerchantId(), IpUtil.getIpAddress(request));
        return RespBody.success();
    }

    @PostMapping(value = "/unbind", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("解绑")
    public RespBody<Void> unbind(@RequestBody @Validated MerchantUnbindDTO dto) {
        merchantService.unbind(SecurityHolder.getMerchantId(), dto.getSmsCode());
        return RespBody.success();
    }

    @PostMapping(value = "/withdrawWay", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("修改提现方式")
    public RespBody<Void> withdrawWay(@RequestBody @Validated MerchantWithdrawChangeRequest request) {
        merchantService.changeWithdrawWay(SecurityHolder.getMerchantId(), request.getWithdrawWay());
        return RespBody.success();
    }

    @GetMapping("/generate")
    @ApiOperation("生成二维码")
    public RespBody<MerchantAuthResponse> generate() {
        MerchantAuthResponse vo = merchantService.generateAuthCode(SecurityHolder.getMerchantId());
        return RespBody.success(vo);
    }
}
