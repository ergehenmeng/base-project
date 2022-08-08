package com.eghm.dao.mapper;

import com.eghm.dao.model.TicketOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 门票订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-12
 */
public interface TicketOrderMapper extends BaseMapper<TicketOrder> {

    /**
     * 查询所有支付处理中订单编号
     * @return 订单编号列表
     */
    List<String> getPayingList();
}
