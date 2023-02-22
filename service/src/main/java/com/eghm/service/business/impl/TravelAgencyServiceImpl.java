package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.RoleType;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.mapper.TravelAgencyMapper;
import com.eghm.model.Merchant;
import com.eghm.model.TravelAgency;
import com.eghm.model.dto.business.travel.TravelAgencyAddRequest;
import com.eghm.model.dto.business.travel.TravelAgencyEditRequest;
import com.eghm.model.dto.business.travel.TravelAgencyQueryRequest;
import com.eghm.service.business.MerchantInitService;
import com.eghm.service.business.TravelAgencyService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @date 2023/2/18
 */
@Service("travelAgencyService")
@Slf4j
@AllArgsConstructor
public class TravelAgencyServiceImpl implements TravelAgencyService, MerchantInitService {
    
    private final TravelAgencyMapper travelAgencyMapper;
    
    @Override
    public Page<TravelAgency> getByPage(TravelAgencyQueryRequest request) {
        LambdaQueryWrapper<TravelAgency> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, TravelAgency::getState, request.getState());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), TravelAgency::getTitle, request.getQueryName());
        return travelAgencyMapper.selectPage(request.createPage(), wrapper);
    }
    
    @Override
    public void create(TravelAgencyAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        TravelAgency agency = DataUtil.copy(request, TravelAgency.class);
        agency.setState(State.UN_SHELVE);
        agency.setPlatformState(PlatformState.SHELVE);
        travelAgencyMapper.insert(agency);
    }
    
    @Override
    public void update(TravelAgencyEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        TravelAgency travelAgency = travelAgencyMapper.selectById(request.getId());
        TravelAgency agency = DataUtil.copy(request, TravelAgency.class);
        if (travelAgency.getState() == State.INIT) {
            agency.setState(State.UN_SHELVE);
        }
        travelAgencyMapper.updateById(agency);
    }
    
    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<TravelAgency> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(TravelAgency::getId, id);
        wrapper.set(TravelAgency::getState, state);
        travelAgencyMapper.update(null, wrapper);
    }
    
    @Override
    public void updateAuditState(Long id, PlatformState state) {
        LambdaUpdateWrapper<TravelAgency> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(TravelAgency::getId, id);
        wrapper.set(TravelAgency::getPlatformState, state);
        travelAgencyMapper.update(null, wrapper);
    }
    
    @Override
    public TravelAgency selectByIdRequired(Long id) {
        TravelAgency travelAgency = travelAgencyMapper.selectById(id);
        if (travelAgency == null) {
            log.info("旅行社商家信息未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.TRAVEL_AGENCY_NOT_FOUND);
        }
        return travelAgency;
    }
    
    @Override
    public void deleteById(Long id) {
        travelAgencyMapper.deleteById(id);
    }
    
    @Override
    public void init(Merchant merchant) {
        TravelAgency agency = new TravelAgency();
        agency.setMerchantId(merchant.getId());
        agency.setState(State.INIT);
        agency.setPlatformState(PlatformState.SHELVE);
        travelAgencyMapper.insert(agency);
    }
    
    @Override
    public boolean support(List<RoleType> roleTypes) {
        return roleTypes.contains(RoleType.LINE);
    }
    
    /**
     * 检验名称是否重复
     * @param title 旅行社名称
     * @param id id 编辑时不能为空
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<TravelAgency> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TravelAgency::getTitle, title);
        wrapper.ne(id != null, TravelAgency::getId, id);
        Integer count = travelAgencyMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("旅行社名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.TRAVEL_AGENCY_REDO);
        }
    }
}
