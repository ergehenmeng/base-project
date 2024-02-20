package com.eghm.web.controller.business;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.account.score.ScoreBalanceRechargeDTO;
import com.eghm.dto.business.account.score.ScoreWithdrawApplyDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.ScoreAccount;
import com.eghm.service.business.ScoreAccountService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.account.ScoreAccountResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2024/2/16
 */

@RestController
@Api(tags = "商户积分")
@AllArgsConstructor
@RequestMapping("/manage/merchant/score")
public class ScoreAccountController {

    private final ScoreAccountService scoreAccountService;

    @GetMapping("/account")
    @ApiOperation("积分中心")
    public RespBody<ScoreAccountResponse> account() {
        ScoreAccount account = scoreAccountService.getAccount(SecurityHolder.getMerchantId());
        return RespBody.success(DataUtil.copy(account, ScoreAccountResponse.class));
    }

    @PostMapping("/withdraw/apply")
    @ApiOperation("提现申请")
    public RespBody<Void> apply(@Validated @RequestBody ScoreWithdrawApplyDTO dto) {
        dto.setMerchantId(SecurityHolder.getMerchantId());
        scoreAccountService.applyWithdraw(dto);
        return RespBody.success();
    }

    @PostMapping("/balanceRecharge")
    @ApiOperation("余额充值")
    public RespBody<Void> balanceRecharge(@Validated @RequestBody ScoreBalanceRechargeDTO dto) {
        dto.setMerchantId(SecurityHolder.getMerchantId());
        scoreAccountService.orderComplete(dto.getMerchantId(), dto.getAmount());
        return RespBody.success();
    }

}
