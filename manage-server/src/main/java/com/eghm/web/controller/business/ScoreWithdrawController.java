package com.eghm.web.controller.business;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.withdraw.score.ScoreWithdrawApplyDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ScoreAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */
@RestController
@Api(tags = "商户积分提现")
@AllArgsConstructor
@RequestMapping("/manage/merchant/score/withdraw")
public class ScoreWithdrawController {

    private final ScoreAccountService scoreAccountService;

    @PostMapping("/apply")
    @ApiOperation("提现申请")
    public RespBody<Void> apply(@Validated @RequestBody ScoreWithdrawApplyDTO dto) {
        dto.setMerchantId(SecurityHolder.getMerchantId());
        scoreAccountService.applyWithdraw(dto);
        return RespBody.success();
    }

}
