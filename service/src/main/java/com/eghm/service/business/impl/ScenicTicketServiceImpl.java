package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketAddRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketEditRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketQueryRequest;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.State;
import com.eghm.enums.ref.TicketType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderEvaluationMapper;
import com.eghm.mapper.ScenicTicketMapper;
import com.eghm.model.Scenic;
import com.eghm.model.ScenicTicket;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.ScenicService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.service.business.TicketCombineService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DateUtil;
import com.eghm.utils.DecimalUtil;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.evaluation.AvgScoreVO;
import com.eghm.vo.business.scenic.ticket.CombineTicketVO;
import com.eghm.vo.business.scenic.ticket.TicketResponse;
import com.eghm.vo.business.scenic.ticket.TicketVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/15
 */
@Service("scenicTicketService")
@AllArgsConstructor
@Slf4j
public class ScenicTicketServiceImpl implements ScenicTicketService {

    private final ScenicService scenicService;

    private final CommonService commonService;

    private final ScenicTicketMapper scenicTicketMapper;

    private final TicketCombineService ticketCombineService;

    private final OrderEvaluationMapper orderEvaluationMapper;

    @Override
    public Page<TicketResponse> getByPage(ScenicTicketQueryRequest request) {
        return scenicTicketMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<TicketResponse> getList(ScenicTicketQueryRequest request) {
        return scenicTicketMapper.getByPage(request.createNullPage(), request).getRecords();
    }

    @Override
    public void create(ScenicTicketAddRequest request) {
        Long merchantId = SecurityHolder.getMerchantId();
        this.redoTitle(request.getTitle(), request.getScenicId(), null);
        this.checkScenic(request.getScenicId(), merchantId);
        ScenicTicket ticket = DataUtil.copy(request, ScenicTicket.class);
        ticket.setMerchantId(merchantId);
        ticket.setTotalNum(request.getVirtualNum());
        ticket.setCreateDate(LocalDate.now());
        ticket.setCreateMonth(LocalDate.now().format(DateUtil.MIN_FORMAT));
        scenicTicketMapper.insert(ticket);
        scenicService.updatePrice(request.getScenicId());
        if (ticket.getCategory() == TicketType.COMBINE) {
            ticketCombineService.insert(ticket.getId(), request.getTicketIds());
        }
    }

    @Override
    public void update(ScenicTicketEditRequest request) {
        Long merchantId = SecurityHolder.getMerchantId();
        this.redoTitle(request.getTitle(), request.getScenicId(), request.getId());
        this.checkScenic(request.getScenicId(), merchantId);
        ScenicTicket scenicTicket = this.selectByIdRequired(request.getId());
        commonService.checkIllegal(scenicTicket.getMerchantId());
        ScenicTicket ticket = DataUtil.copy(request, ScenicTicket.class);
        // 总销量要根据真实销量计算
        ticket.setTotalNum(request.getVirtualNum() + scenicTicket.getSaleNum());
        scenicTicketMapper.updateById(ticket);
        scenicService.updatePrice(request.getScenicId());
        if (ticket.getCategory() == TicketType.COMBINE) {
            ticketCombineService.insert(ticket.getId(), request.getTicketIds());
        }
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
        ScenicTicket ticket = scenicTicketMapper.selectById(id);
        if (ticket == null || ticket.getState() != State.SHELVE) {
            log.info("景区门票已下架 [{}]", id);
            throw new BusinessException(ErrorCode.TICKET_DOWN);
        }
        return ticket;
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<ScenicTicket> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ScenicTicket::getId, id);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, ScenicTicket::getMerchantId, merchantId);
        wrapper.set(ScenicTicket::getState, state);
        scenicTicketMapper.update(null, wrapper);
    }

    @Override
    public TicketVO detailById(Long id) {
        ScenicTicket ticket = this.selectByIdShelve(id);
        TicketVO vo = DataUtil.copy(ticket, TicketVO.class);
        if (ticket.getCategory() == TicketType.COMBINE) {
            List<ScenicTicket> ticketList = scenicTicketMapper.getCombineList(id);
            vo.setCombineList(DataUtil.copy(ticketList, CombineTicketVO.class));
        }
        return vo;
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
            wrapper.eq(ScenicTicket::getMerchantId, SecurityHolder.getMerchantId());
            wrapper.set(ScenicTicket::getState, State.UN_SHELVE);
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
        return scenicTicketMapper.getProductPage(Boolean.TRUE.equals(request.getLimit()) ? request.createPage() : request.createNullPage(), request);
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
     * @param merchantId 当前登陆的景区
     */
    private void checkScenic(Long id, Long merchantId) {
        Scenic scenic = scenicService.selectByIdRequired(id);
        if (commonService.checkIsIllegal(scenic.getMerchantId(), merchantId)) {
            log.info("选择的旅行社不属于自己的 [{}] [{}]", scenic.getMerchantId(), merchantId);
            throw new BusinessException(ErrorCode.TRAVEL_AGENCY_NOT_FOUND);
        }
    }
}
