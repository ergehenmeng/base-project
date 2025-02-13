package com.eghm.service.business;

import java.util.List;

/**
 * <p>
 * 组合票关联表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-23
 */
public interface TicketCombineService {

    /**
     * 新增组合票关联信息
     *
     * @param ticketId 门票ID
     * @param combineTicketIds 关联的门票ID集合
     */
    void insert(Long ticketId, List<Long> combineTicketIds);

    /**
     * 根据门票ID获取关联的组合票ID集合
     *
     * @param ticketId 门票ID
     * @return 组合票关联的门票ID
     */
    List<Long> getCombineTicketIds(Long ticketId);
}
