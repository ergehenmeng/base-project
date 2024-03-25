package com.eghm.mapper;

import com.eghm.model.OrderAdjustLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.order.adjust.OrderAdjustResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单改价记录表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-25
 */
public interface OrderAdjustLogMapper extends BaseMapper<OrderAdjustLog> {

    /**
     * 获取改价记录
     *
     * @param orderNo 订单编号
     * @return 改价记录
     */
    List<OrderAdjustResponse> getList(@Param("orderNo") String orderNo);
}
