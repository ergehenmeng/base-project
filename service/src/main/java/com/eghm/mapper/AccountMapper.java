package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.Account;

/**
 * <p>
 * 商户账户信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
public interface AccountMapper extends BaseMapper<Account> {

    /**
     * 更新账户信息
     * @param account 账户信息
     */
    int updateAccount(Account account);
}
