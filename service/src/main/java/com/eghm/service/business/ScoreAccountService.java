package com.eghm.service.business;

import com.eghm.dto.business.account.score.ScoreAccountDTO;
import com.eghm.model.ScoreAccount;

/**
 * <p>
 * 商户积分账户表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-12
 */
public interface ScoreAccountService {

    /**
     * 更新商户积分账户
     * 注意: 该方法只有积分真是变动时才会记录变动记录和余额信息, 支付和退款不做记录(订单完成才会触发)
     * @param dto 积分信息
     */
    void updateAccount(ScoreAccountDTO dto);

    /**
     * 查询商户积分信息
     *
     * @param merchantId 商户id
     * @return 积分信息
     */
    ScoreAccount getAccount(Long merchantId);

}
