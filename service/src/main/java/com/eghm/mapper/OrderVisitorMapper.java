package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.enums.ref.VisitorState;
import com.eghm.model.OrderVisitor;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单游客信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-27
 */
public interface OrderVisitorMapper extends BaseMapper<OrderVisitor> {

    /**
     * 将退款申请中的游客信息更新为已退款
     *
     * @param orderNo  订单号
     * @param refundId 退款记录
     * @param state    state
     */
    void refundVisitor(@Param("orderNo") String orderNo, @Param("refundId") Long refundId, @Param("state") VisitorState state);
}
