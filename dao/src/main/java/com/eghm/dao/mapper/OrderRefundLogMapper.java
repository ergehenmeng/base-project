package com.eghm.dao.mapper;

import com.eghm.dao.model.OrderRefundLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.dto.ext.OrderRefund;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * 统计订单总退款数量 包含退款成功和退款中
     * @param orderNo 订单编号
     * @return 数量
     */
    int getTotalRefundNum(@Param("orderNo") String orderNo);

    /**
     * 查询门票退款处理中的订单流水号及退款流水号
     * @return 流水号信息
     */
    List<OrderRefund> getTicketRefunding();
}
