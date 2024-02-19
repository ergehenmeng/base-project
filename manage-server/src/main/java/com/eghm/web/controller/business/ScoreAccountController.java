package com.eghm.web.controller.business;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.ScoreAccount;
import com.eghm.service.business.ScoreAccountService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.account.ScoreAccountResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
