package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.freeze.AccountFreezeDTO;
import com.eghm.dto.business.freeze.AccountFreezeQueryRequest;
import com.eghm.dto.business.freeze.CompleteChangeDTO;
import com.eghm.dto.business.freeze.RefundChangeDTO;
import com.eghm.model.AccountFreezeLog;
import com.eghm.vo.business.freeze.AccountFreezeLogResponse;

import java.util.List;

/**
 * <p>
 * 商户资金冻结记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-28
 */
public interface AccountFreezeLogService {

    /**
     * 分页查询冻结记录
     *
     * @param request 查询条件
     * @return 分页结果
     */
    Page<AccountFreezeLogResponse> getByPage(AccountFreezeQueryRequest request);

    /**
     * 查询冻结记录 (不分页)
     *
     * @param request 查询条件
     * @return 导出列表
     */
    List<AccountFreezeLogResponse> getList(AccountFreezeQueryRequest request);

    /**
     * 添加冻结记录
     *
     * @param dto 冻结记录
     */
    void addFreezeLog(AccountFreezeDTO dto);

    /**
     * 退款更新冻结金额
     *
     * @param dto 退款信息
     */
    void refund(RefundChangeDTO dto);

    /**
     * 订单完成解冻
     *
     * @param merchantId 商户ID
     * @param orderNo 订单号
     * @return 冻结信息
     */
    AccountFreezeLog complete(Long merchantId, String orderNo);
}
