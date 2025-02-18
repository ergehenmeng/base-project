package com.eghm.web.controller.business;

import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.model.Account;
import com.eghm.service.business.AccountService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.account.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@RestController
@Tag(name="商户资金")
@AllArgsConstructor
@RequestMapping(value = "/manage/merchant", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/account")
    @Operation(summary = "账户中心")
    public RespBody<AccountResponse> account() {
        Account account = accountService.getAccount(SecurityHolder.getMerchantId());
        return RespBody.success(DataUtil.copy(account, AccountResponse.class));
    }

}
