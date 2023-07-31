package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.ticket.TicketOrderQueryDTO;
import com.eghm.dto.business.order.ticket.TicketOrderQueryRequest;
import com.eghm.model.TicketOrder;
import com.eghm.vo.business.order.ticket.TicketOrderResponse;
import com.eghm.vo.business.order.ticket.TicketOrderVO;
import org.apache.ibatis.annotations.Param;

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
     * 分页查询门票订单
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<TicketOrderResponse> getByPage(Page<TicketOrderResponse> page, @Param("param") TicketOrderQueryRequest request);

    /**
     * 查询用户门票订单列表
     * @param page 分页信息
     * @param dto 查询条件
     * @return 列表
     */
    Page<TicketOrderVO> getList(Page<TicketOrderVO> page, @Param("param") TicketOrderQueryDTO dto);
}
