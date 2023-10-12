package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.scenic.ticket.ScenicTicketAddRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketEditRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ScenicTicketMapper;
import com.eghm.model.Scenic;
import com.eghm.model.ScenicTicket;
import com.eghm.service.business.ScenicService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.scenic.ticket.ScenicTicketResponse;
import com.eghm.vo.business.scenic.ticket.TicketBaseVO;
import com.eghm.vo.business.scenic.ticket.TicketVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/15
 */
@Service("scenicTicketService")
@AllArgsConstructor
@Slf4j
public class ScenicTicketServiceImpl implements ScenicTicketService {

    private final ScenicTicketMapper scenicTicketMapper;

    private final ScenicService scenicService;

    @Override
    public Page<ScenicTicketResponse> getByPage(ScenicTicketQueryRequest request) {
        return scenicTicketMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void createTicket(ScenicTicketAddRequest request) {
        Long merchantId = SecurityHolder.getMerchantId();
        this.redoTitle(request.getTitle(), merchantId,null);
        this.checkScenic(request.getScenicId());
        ScenicTicket ticket = DataUtil.copy(request, ScenicTicket.class);
        ticket.setMerchantId(merchantId);
        scenicTicketMapper.insert(ticket);
        scenicService.updatePrice(request.getScenicId());
    }

    @Override
    public void updateTicket(ScenicTicketEditRequest request) {
        this.redoTitle(request.getTitle(), SecurityHolder.getMerchantId(), request.getId());
        this.checkScenic(request.getScenicId());
        ScenicTicket ticket = DataUtil.copy(request, ScenicTicket.class);
        scenicTicketMapper.updateById(ticket);
        scenicService.updatePrice(request.getScenicId());
    }

    @Override
    public ScenicTicket selectByIdRequired(Long id) {
        ScenicTicket ticket = scenicTicketMapper.selectById(id);
        if (ticket == null) {
            log.error("门票信息未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.TICKET_DOWN);
        }
        return ticket;
    }

    @Override
    public ScenicTicket selectByIdShelve(Long id) {
        ScenicTicket ticket = this.selectByIdRequired(id);
        if (ticket.getPlatformState() != PlatformState.SHELVE) {
            log.info("景区门票已下架 [{}]", id);
            throw new BusinessException(ErrorCode.TICKET_DOWN);
        }
        return ticket;
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<ScenicTicket> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ScenicTicket::getId, id);
        wrapper.set(ScenicTicket::getState, state);
        scenicTicketMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        ScenicTicket ticket = scenicTicketMapper.selectById(id);
        if (ticket.getState() != State.SHELVE) {
            log.info("门票尚未提交审核 [{}]", id);
            throw new BusinessException(ErrorCode.SCENIC_TICKET_NOT_UP);
        }
        LambdaUpdateWrapper<ScenicTicket> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ScenicTicket::getId, id);
        wrapper.set(ScenicTicket::getPlatformState, state);
        scenicTicketMapper.update(null, wrapper);
    }

    @Override
    public List<TicketBaseVO> getTicketList(Long scenicId) {
        return scenicTicketMapper.getTicketList(scenicId);
    }

    @Override
    public TicketVO detailById(Long id) {
        ScenicTicket ticket = this.selectByIdShelve(id);
        return DataUtil.copy(ticket, TicketVO.class);
    }

    @Override
    public void updateStock(Long id, Integer num) {
        int stock = scenicTicketMapper.updateStock(id, num);
        if (stock != 1) {
            log.error("更新门票库存失败 [{}] [{}] [{}]", id, num, stock);
            throw new BusinessException(ErrorCode.TICKET_STOCK);
        }
    }

    @Override
    public void deleteById(Long id) {
        ScenicTicket ticket = scenicTicketMapper.selectById(id);
        if (ticket != null) {
            scenicTicketMapper.deleteById(id);
            scenicService.updatePrice(ticket.getScenicId());
        }
    }

    @Override
    public void updateScore(Long productId, BigDecimal score) {
        LambdaUpdateWrapper<ScenicTicket> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ScenicTicket::getId, productId);
        wrapper.set(ScenicTicket::getScore, score);
        scenicTicketMapper.update(null, wrapper);
    }

    /**
     * 新增编辑时判断门票名称是否重复
     * @param title 门票
     * @param id id
     */
    private void redoTitle(String title, Long merchantId, Long id) {
        LambdaQueryWrapper<ScenicTicket> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ScenicTicket::getTitle, title);
        wrapper.eq(ScenicTicket::getMerchantId, merchantId);
        wrapper.ne(id != null, ScenicTicket::getId, id);
        Long count = scenicTicketMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.SCENIC_TICKET_REDO);
        }
    }

    /**
     * 校验门票所属景区是否存在
     * @param id 景区id
     */
    private void checkScenic(Long id) {
        Scenic scenic = scenicService.selectById(id);
        if (scenic == null) {
            log.info("门票绑定的景区不存在或已被删除 [{}]", id);
            throw new BusinessException(ErrorCode.SCENIC_DELETE);
        }
    }
}
