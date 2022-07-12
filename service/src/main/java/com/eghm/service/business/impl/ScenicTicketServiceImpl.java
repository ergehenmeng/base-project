package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.State;
import com.eghm.dao.mapper.ScenicTicketMapper;
import com.eghm.dao.model.ScenicTicket;
import com.eghm.model.dto.business.scenic.ticket.ScenicTicketAddRequest;
import com.eghm.model.dto.business.scenic.ticket.ScenicTicketEditRequest;
import com.eghm.model.dto.business.scenic.ticket.ScenicTicketQueryRequest;
import com.eghm.model.vo.business.scenic.ticket.ScenicTicketResponse;
import com.eghm.model.vo.scenic.ticket.TicketBaseVO;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/15 21:11
 */
@Service("scenicTicketService")
@AllArgsConstructor
public class ScenicTicketServiceImpl implements ScenicTicketService {

    private final ScenicTicketMapper scenicTicketMapper;

    @Override
    public Page<ScenicTicketResponse> getByPage(ScenicTicketQueryRequest request) {
        return scenicTicketMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void createTicket(ScenicTicketAddRequest request) {
        ScenicTicket ticket = DataUtil.copy(request, ScenicTicket.class);
        scenicTicketMapper.insert(ticket);
    }

    @Override
    public void updateTicket(ScenicTicketEditRequest request) {
        ScenicTicket ticket = DataUtil.copy(request, ScenicTicket.class);
        scenicTicketMapper.updateById(ticket);
    }

    @Override
    public ScenicTicket selectById(Long id) {
        return scenicTicketMapper.selectById(id);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<ScenicTicket> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ScenicTicket::getId, id);
        wrapper.set(ScenicTicket::getState, state);
        scenicTicketMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, AuditState state) {
        LambdaUpdateWrapper<ScenicTicket> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ScenicTicket::getId, id);
        wrapper.set(ScenicTicket::getAuditState, state);
        scenicTicketMapper.update(null, wrapper);
    }

    @Override
    public List<TicketBaseVO> getTicketList(Long scenicId) {
        return scenicTicketMapper.getTicketList(scenicId);
    }
}
