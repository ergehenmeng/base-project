package com.eghm.dao.mapper;

import com.eghm.dao.model.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-17
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 查询所有支付处理中订单编号
     * @return 订单编号列表
     */
    List<String> getPayingList();
}
