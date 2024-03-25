package com.eghm.web.controller.business;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.account.score.ScoreRechargeDTO;
import com.eghm.dto.business.account.score.ScoreScanRechargeDTO;
import com.eghm.dto.business.account.score.ScoreWithdrawApplyDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.ScoreAccount;
import com.eghm.service.business.ScoreAccountService;
import com.eghm.pay.vo.PrepayVO;
import com.eghm.utils.DataUtil;
import com.eghm.utils.IpUtil;
import com.eghm.vo.business.account.ScoreAccountResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 二哥很猛
 * @since 2024/2/16
 */

@RestController
@Api(tags = "商户积分")
@AllArgsConstructor
@RequestMapping(value = "/manage/merchant/score", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScoreAccountController {

    private final ScoreAccountService scoreAccountService;

    @GetMapping("/account")
    @ApiOperation("积分中心")
    public RespBody<ScoreAccountResponse> account() {
        ScoreAccount account = scoreAccountService.getAccount(SecurityHolder.getMerchantId());
        return RespBody.success(DataUtil.copy(account, ScoreAccountResponse.class));
    }

    @PostMapping(value = "/withdraw/apply", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("提现申请")
    public RespBody<Void> apply(@Validated @RequestBody ScoreWithdrawApplyDTO dto) {
        dto.setMerchantId(SecurityHolder.getMerchantId());
        scoreAccountService.applyWithdraw(dto);
        return RespBody.success();
    }

    @PostMapping(value = "/recharge/balance", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("余额充值")
    public RespBody<Void> rechargeBalance(@Validated @RequestBody ScoreRechargeDTO dto) {
        dto.setMerchantId(SecurityHolder.getMerchantId());
        scoreAccountService.rechargeBalance(dto);
        return RespBody.success();
    }

    @PostMapping(value = "/recharge/scan", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("扫码充值")
    public RespBody<String> rechargeScan(@Validated @RequestBody ScoreScanRechargeDTO dto, HttpServletRequest request) {
        dto.setMerchantId(SecurityHolder.getMerchantId());
        dto.setClientIp(IpUtil.getIpAddress(request));
        PrepayVO prepayVO = scoreAccountService.rechargeScan(dto);
        QrConfig config = new QrConfig();
        config.setHeight(200);
        config.setWidth(200);
        String generate = QrCodeUtil.generateAsBase64(prepayVO.getQrCodeUrl(), config, ImgUtil.IMAGE_TYPE_JPG);
        return RespBody.success(generate);
    }

}
