package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.withdraw.WithdrawApplyDTO;
import com.eghm.dto.business.withdraw.WithdrawQueryRequest;
import com.eghm.model.WithdrawLog;

/**
 * <p>
 * 商户提现记录 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-16
 */
public interface WithdrawService {

    /**
     * 获取提现记录
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<WithdrawLog> getByPage(WithdrawQueryRequest request);

    /**
     * 申请提现
     *
     * @param dto 提现信息
     */
    void apply(WithdrawApplyDTO dto);
}
