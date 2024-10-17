package com.eghm.web.controller.business;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.account.score.ScoreRechargeDTO;
import com.eghm.dto.business.account.score.ScoreScanRechargeDTO;
import com.eghm.dto.business.account.score.ScoreWithdrawApplyDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ErrorCode;
import com.eghm.model.Account;
import com.eghm.model.ScoreAccount;
import com.eghm.pay.vo.PrepayVO;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.ScoreAccountService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import com.eghm.utils.IpUtil;
import com.eghm.vo.business.account.ScoreAccountResponse;
import com.eghm.vo.business.account.ScoreRechargeResponse;
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

    private final SysConfigApi sysConfigApi;

    private final AccountService accountService;

    private final ScoreAccountService scoreAccountService;

    @GetMapping("/account")
    @ApiOperation("积分中心")
    public RespBody<ScoreAccountResponse> account() {
        ScoreAccount account = scoreAccountService.getAccount(SecurityHolder.getMerchantId());
        return RespBody.success(DataUtil.copy(account, ScoreAccountResponse.class));
    }

    @GetMapping(value = "/withdraw/detail")
    @ApiOperation("提现信息")
    public RespBody<String> withdrawDetail() {
        Integer withdraw = this.getScoreMinWithdraw();
        return RespBody.success(DecimalUtil.centToYuan(withdraw));
    }

    @PostMapping(value = "/withdraw/apply", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("提现申请")
    public RespBody<Void> apply(@Validated @RequestBody ScoreWithdrawApplyDTO dto) {
        Integer withdraw = this.getScoreMinWithdraw();
        if (dto.getAmount() < withdraw) {
            return RespBody.error(ErrorCode.SCORE_WITHDRAW_MIN, DecimalUtil.centToYuan(withdraw));
        }
        dto.setMerchantId(SecurityHolder.getMerchantId());
        scoreAccountService.applyWithdraw(dto);
        return RespBody.success();
    }

    @GetMapping(value = "/recharge/detail")
    @ApiOperation("充值信息")
    public RespBody<ScoreRechargeResponse> rechargeDetail() {
        ScoreAccount scoreAccount = scoreAccountService.getAccount(SecurityHolder.getMerchantId());
        Account account = accountService.getAccount(SecurityHolder.getMerchantId());
        ScoreRechargeResponse response = new ScoreRechargeResponse();
        response.setAmount(account.getAmount());
        response.setScoreAmount(scoreAccount.getAmount());
        response.setMinRecharge(this.getScoreMinRecharge());
        return RespBody.success(response);
    }

    @PostMapping(value = "/recharge/balance", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("余额充值")
    public RespBody<Void> rechargeBalance(@Validated @RequestBody ScoreRechargeDTO dto) {
        Integer recharge = this.getScoreMinRecharge();
        if (dto.getAmount() < recharge) {
            return RespBody.error(ErrorCode.SCORE_RECHARGE_MIN, DecimalUtil.centToYuan(recharge));
        }
        dto.setMerchantId(SecurityHolder.getMerchantId());
        scoreAccountService.rechargeBalance(dto);
        return RespBody.success();
    }

    @PostMapping(value = "/recharge/scan", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("扫码充值")
    public RespBody<String> rechargeScan(@Validated @RequestBody ScoreScanRechargeDTO dto, HttpServletRequest request) {
        Integer recharge = this.getScoreMinRecharge();
        if (dto.getAmount() < recharge) {
            return RespBody.error(ErrorCode.SCORE_RECHARGE_MIN, DecimalUtil.centToYuan(recharge));
        }
        dto.setMerchantId(SecurityHolder.getMerchantId());
        dto.setClientIp(IpUtil.getIpAddress(request));
        try {
            PrepayVO prepayVO = scoreAccountService.rechargeScan(dto);
            QrConfig config = new QrConfig();
            config.setHeight(200);
            config.setWidth(200);
            String generate = QrCodeUtil.generateAsBase64(prepayVO.getQrCodeUrl(), config, ImgUtil.IMAGE_TYPE_JPG);
            return RespBody.success(generate);
        } catch (Exception e) {
            return RespBody.error(ErrorCode.RECHARGE_CREATE_ERROR);
        }
    }

    /**
     * 获取最低充值金额 单位:分
     *
     * @return 钱
     */
    private Integer getScoreMinRecharge() {
        return sysConfigApi.getInt(ConfigConstant.SCORE_MIN_RECHARGE, 1000);
    }

    /**
     * 获取最低提现金额 单位:分
     *
     * @return 钱
     */
    private Integer getScoreMinWithdraw() {
        return sysConfigApi.getInt(ConfigConstant.SCORE_MIN_WITHDRAW, 1000);
    }
}
