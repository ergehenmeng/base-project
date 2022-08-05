package com.eghm.dao.mapper;

import com.eghm.dao.model.OrderRefundLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单退款记录表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-01
 */
public interface OrderRefundLogMapper extends BaseMapper<OrderRefundLog> {

    /**
     * 统计订单总退款金额 包含退款成功和退款中
     * @param orderId 订单id
     * @return 退款金额
     */
    int getTotalRefundAmount(@Param("orderId") Long orderId);
}
