package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketAddRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketEditRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketQueryRequest;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderEvaluationMapper;
import com.eghm.mapper.ScenicTicketMapper;
import com.eghm.model.Scenic;
import com.eghm.model.ScenicTicket;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.ScenicService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.evaluation.AvgScoreVO;
import com.eghm.vo.business.scenic.ticket.ScenicTicketResponse;
import com.eghm.vo.business.scenic.ticket.TicketVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author 二哥很猛 2022/6/15
 */
@Service("scenicTicketService")
@AllArgsConstructor
@Slf4j
public class ScenicTicketServiceImpl implements ScenicTicketService {

    private final ScenicTicketMapper scenicTicketMapper;

    private final ScenicService scenicService;

    private final CommonService commonService;

    private final OrderEvaluationMapper orderEvaluationMapper;

    @Override
    public Page<ScenicTicketResponse> getByPage(ScenicTicketQueryRequest request) {
        return scenicTicketMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(ScenicTicketAddRequest request) {
        Long merchantId = SecurityHolder.getMerchantId();
        this.redoTitle(request.getTitle(), request.getScenicId(), null);
        this.checkScenic(request.getScenicId());
        ScenicTicket ticket = DataUtil.copy(request, ScenicTicket.class);
        ticket.setMerchantId(merchantId);
        ticket.setCreateDate(LocalDate.now());
        scenicTicketMapper.insert(ticket);
        scenicService.updatePrice(request.getScenicId());
    }

    @Override
    public void update(ScenicTicketEditRequest request) {
        this.redoTitle(request.getTitle(), request.getScenicId(), request.getId());
        this.checkScenic(request.getScenicId());
        ScenicTicket scenicTicket = this.selectByIdRequired(request.getId());
        commonService.checkIllegal(scenicTicket.getMerchantId());
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
        if (ticket.getState() != State.SHELVE) {
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
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, ScenicTicket::getMerchantId, merchantId);
        scenicTicketMapper.update(null, wrapper);
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
    public void releaseStock(String orderNo, Integer num) {
        scenicTicketMapper.releaseStock(orderNo, num);
    }

    @Override
    public void deleteById(Long id) {
        ScenicTicket ticket = scenicTicketMapper.selectById(id);
        if (ticket != null) {
            scenicService.updatePrice(ticket.getScenicId());
            LambdaUpdateWrapper<ScenicTicket> wrapper = Wrappers.lambdaUpdate();
            wrapper.eq(ScenicTicket::getId, id);
            wrapper.set(ScenicTicket::getState, State.UN_SHELVE);
            wrapper.eq(ScenicTicket::getMerchantId, SecurityHolder.getMerchantId());
            wrapper.set(ScenicTicket::getDeleted, true);
            scenicTicketMapper.update(null, wrapper);
        }
    }

    @Override
    public void updateScore(CalcStatistics vo) {
        AvgScoreVO score = orderEvaluationMapper.getProductScore(vo.getProductId());
        if (score.getNum() < CommonConstant.MIN_SCORE_NUM) {
            log.info("为保证评分系统的公平性, 评价数量小于5条时默认不展示景区评分 [{}]", vo.getProductId());
            return;
        }
        scenicTicketMapper.updateScore(vo.getProductId(), DecimalUtil.calcAvgScore(score.getTotalScore(), score.getNum()));
    }

    @Override
    public Page<BaseProductResponse> getProductPage(BaseProductQueryRequest request) {
        return scenicTicketMapper.getProductPage(request.createPage(), request);
    }

    /**
     * 新增编辑时判断门票名称是否重复
     *
     * @param title 门票
     * @param id    id
     */
    private void redoTitle(String title, Long scenicId, Long id) {
        LambdaQueryWrapper<ScenicTicket> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ScenicTicket::getTitle, title);
        wrapper.eq(ScenicTicket::getScenicId, scenicId);
        wrapper.ne(id != null, ScenicTicket::getId, id);
        Long count = scenicTicketMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.SCENIC_TICKET_REDO);
        }
    }

    /**
     * 校验门票所属景区是否存在
     *
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
