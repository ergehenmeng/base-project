package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.ScoreAccount;

/**
 * <p>
 * 商户积分账户表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-12
 */
public interface ScoreAccountMapper extends BaseMapper<ScoreAccount> {

    /**
     * 更新账户信息
     * @param account 账户信息
     */
    int updateAccount(ScoreAccount account);
}
