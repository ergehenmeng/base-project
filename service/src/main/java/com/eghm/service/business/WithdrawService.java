package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.account.WithdrawNotifyDTO;
import com.eghm.dto.business.withdraw.WithdrawApplyDTO;
import com.eghm.dto.business.withdraw.WithdrawQueryRequest;
import com.eghm.vo.business.withdraw.WithdrawLogResponse;

import java.util.List;

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
    Page<WithdrawLogResponse> getByPage(WithdrawQueryRequest request);

    /**
     * 获取提现记录 不分页
     *
     * @param request 查询条件
     * @return 列表
     */
    List<WithdrawLogResponse> getList(WithdrawQueryRequest request);

    /**
     * 申请提现(计算手续费 + 发起提现)
     * 待补全: 如果是同步到账则将提现冻结金额减少直接减少,否则在异步回调中减少
     * @param dto 提现信息
     */
    void apply(WithdrawApplyDTO dto);

    /**
     * 提现结果通知
     *
     * @param dto 提现信息
     */
    void notify(WithdrawNotifyDTO dto);
}
