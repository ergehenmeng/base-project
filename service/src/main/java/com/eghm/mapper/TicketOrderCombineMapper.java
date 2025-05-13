package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.TicketOrderCombine;
import com.eghm.vo.business.order.ticket.CombineOrderResponse;
import com.eghm.vo.business.scenic.ticket.CombineTicketVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 套票票订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-23
 */
public interface TicketOrderCombineMapper extends BaseMapper<TicketOrderCombine> {

    /**
     * 获取套票票订单信息 (移动端)
     *
     * @param orderNo 订单号
     * @return 套票票订单信息
     */
    List<CombineTicketVO> getList(@Param("orderNo") String orderNo);

    /**
     * 获取套票票订单信息 (管理后台)
     *
     * @param orderNo 订单号
     * @return 套票票订单信息
     */
    List<CombineOrderResponse> getCombineList(@Param("orderNo") String orderNo);
}
