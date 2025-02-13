package com.eghm.mapper;

import com.eghm.model.TicketCombine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组合票关联表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-23
 */
public interface TicketCombineMapper extends BaseMapper<TicketCombine> {

    /**
     * 根据门票id获取关联的组合票id
     *
     * @param ticketId 门票ID
     * @return 组合票ID集合
     */
    List<Long> getCombineTicketIds(@Param("ticketId") Long ticketId);
}
