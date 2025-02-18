package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.TicketOrderSnapshot;
import com.eghm.vo.business.scenic.ticket.CombineTicketVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组合票订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-23
 */
public interface TicketOrderSnapshotMapper extends BaseMapper<TicketOrderSnapshot> {

    /**
     * 获取组合票订单信息
     *
     * @param orderNo 订单号
     * @return 组合票订单信息
     */
    List<CombineTicketVO> getList(@Param("orderNo") String orderNo);
}
