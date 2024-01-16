package com.eghm.service.business;

import com.eghm.dto.business.account.AccountDTO;
import com.eghm.model.Account;

/**
 * <p>
 * 商户账户信息表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
public interface AccountService {

    /**
     * 新增资金变动记录
     *
     * @param dto 变动信息
     */
    void updateAccount(AccountDTO dto);

    /**
     * 查询商户账户信息
     *
     * @param merchantId 商户id
     * @return 账户信息
     */
    Account getAccount(Long merchantId);
}
