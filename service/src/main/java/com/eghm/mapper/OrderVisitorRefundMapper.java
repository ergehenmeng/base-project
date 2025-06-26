package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.OrderVisitorRefund;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 游客退款记录关联表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-01
 */
public interface OrderVisitorRefundMapper extends BaseMapper<OrderVisitorRefund> {

    /**
     * 退款成功更新退款记录关联的游客状态
     *
     * @param orderNo 订单号
     * @param refundId 退款单号
     * @return 1
     */
    int refundSuccess(@Param("orderNo") String orderNo, @Param("refundId") Long refundId);

    /**
     * 退款申请回滚
     *
     * @param refundId 退款id
     */
    void refundRollback(@Param("refundId") Long refundId);
}
