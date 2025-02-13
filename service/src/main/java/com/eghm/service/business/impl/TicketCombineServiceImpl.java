package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.TicketCombineMapper;
import com.eghm.model.TicketCombine;
import com.eghm.service.business.TicketCombineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 组合票关联表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-23
 */
@Service
@AllArgsConstructor
public class TicketCombineServiceImpl implements TicketCombineService {

    private final TicketCombineMapper ticketCombineMapper;

    @Override
    public void insert(Long ticketId, List<Long> combineTicketIds) {
        ticketCombineMapper.delete(Wrappers.lambdaUpdate(TicketCombine.class).eq(TicketCombine::getTicketId, ticketId));
        combineTicketIds.forEach(combineTicketId -> ticketCombineMapper.insert(new TicketCombine(ticketId, combineTicketId)));
    }

    @Override
    public List<Long> getCombineTicketIds(Long ticketId) {
        return ticketCombineMapper.getCombineTicketIds(ticketId);
    }

}
