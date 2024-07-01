package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.travel.TravelAgencyAddRequest;
import com.eghm.dto.business.travel.TravelAgencyEditRequest;
import com.eghm.dto.business.travel.TravelAgencyQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.CollectType;
import com.eghm.enums.ref.RoleType;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.LineMapper;
import com.eghm.mapper.TravelAgencyMapper;
import com.eghm.model.Line;
import com.eghm.model.Merchant;
import com.eghm.model.TravelAgency;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.MemberCollectService;
import com.eghm.service.business.MerchantInitService;
import com.eghm.service.business.TravelAgencyService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.BeanValidator;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.line.BaseTravelResponse;
import com.eghm.vo.business.line.TravelDetailVO;
import com.eghm.vo.business.line.TravelResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.enums.ErrorCode.LINE_DOWN;
import static com.eghm.enums.ErrorCode.STORE_NOT_COMPLETE;

/**
 * @author 殿小二
 * @since 2023/2/18
 */
@Service("travelAgencyService")
@Slf4j
@AllArgsConstructor
public class TravelAgencyServiceImpl implements TravelAgencyService, MerchantInitService {

    private final TravelAgencyMapper travelAgencyMapper;

    private final CommonService commonService;

    private final MemberCollectService memberCollectService;

    private final SysAreaService sysAreaService;

    private final LineMapper lineMapper;

    @Override
    public Page<TravelResponse> getByPage(TravelAgencyQueryRequest request) {
        Page<TravelResponse> byPage = travelAgencyMapper.getByPage(request.createPage(), request);
        byPage.getRecords().forEach(agency -> agency.setDetailAddress(sysAreaService.parseArea(agency.getCityId(), agency.getCountyId(), agency.getDetailAddress())));
        return byPage;
    }

    @Override
    public List<BaseTravelResponse> getList(Long merchantId) {
        return travelAgencyMapper.getBaseList(merchantId);
    }

    @Override
    public void create(TravelAgencyAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        TravelAgency agency = DataUtil.copy(request, TravelAgency.class);
        agency.setState(State.UN_SHELVE);
        agency.setCoverUrl(CollUtil.join(request.getCoverList(), CommonConstant.COMMA));
        travelAgencyMapper.insert(agency);
    }

    @Override
    public void update(TravelAgencyEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        TravelAgency travelAgency = this.selectByIdRequired(request.getId());
        commonService.checkIllegal(travelAgency.getMerchantId());
        TravelAgency agency = DataUtil.copy(request, TravelAgency.class);
        agency.setCoverUrl(CollUtil.join(request.getCoverList(), CommonConstant.COMMA));
        travelAgencyMapper.updateById(agency);
    }

    @Override
    public void updateState(Long id, State state) {
        if (state == State.SHELVE) {
            TravelAgency agency = this.selectByIdRequired(id);
            BeanValidator.validate(agency, STORE_NOT_COMPLETE);
        }
        LambdaUpdateWrapper<TravelAgency> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(TravelAgency::getId, id);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, TravelAgency::getMerchantId, merchantId);
        wrapper.set(TravelAgency::getState, state);
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
    public TravelAgency selectByIdShelve(Long id) {
        TravelAgency travelAgency = travelAgencyMapper.selectById(id);
        if (travelAgency == null || travelAgency.getState() != State.SHELVE) {
            log.error("该旅行社商品已下架 [{}]", id);
            throw new BusinessException(LINE_DOWN);
        }
        return travelAgency;
    }

    @Override
    public void deleteById(Long id) {
        LambdaUpdateWrapper<TravelAgency> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(TravelAgency::getId, id);
        wrapper.eq(TravelAgency::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.set(TravelAgency::getState, State.UN_SHELVE);
        wrapper.set(TravelAgency::getDeleted, true);
        travelAgencyMapper.update(null, wrapper);
    }

    @Override
    public TravelDetailVO detail(Long id) {
        TravelAgency travelAgency = this.selectByIdShelve(id);
        TravelDetailVO vo = DataUtil.copy(travelAgency, TravelDetailVO.class);
        vo.setDetailAddress(sysAreaService.parseArea(travelAgency.getCityId(), travelAgency.getCountyId(), vo.getDetailAddress()));
        vo.setCollect(memberCollectService.checkCollect(id, CollectType.TRAVEL_AGENCY));
        return vo;
    }

    @Override
    public Page<BaseStoreResponse> getStorePage(BaseStoreQueryRequest request) {
        return travelAgencyMapper.getStorePage(Boolean.TRUE.equals(request.getLimit()) ? request.createPage() : request.createNullPage(), request);
    }

    @Override
    public void logout(Long merchantId) {
        LambdaUpdateWrapper<TravelAgency> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(TravelAgency::getMerchantId, merchantId);
        wrapper.set(TravelAgency::getState, State.FORCE_UN_SHELVE);
        travelAgencyMapper.update(null, wrapper);

        LambdaUpdateWrapper<Line> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(Line::getMerchantId, merchantId);
        updateWrapper.set(Line::getState, State.FORCE_UN_SHELVE);
        lineMapper.update(null, updateWrapper);
    }

    @Override
    public void init(Merchant merchant) {
        TravelAgency agency = new TravelAgency();
        agency.setMerchantId(merchant.getId());
        travelAgencyMapper.insert(agency);
    }

    @Override
    public boolean support(List<RoleType> roleTypes) {
        return roleTypes.contains(RoleType.LINE);
    }

    /**
     * 检验名称是否重复
     *
     * @param title 旅行社名称
     * @param id    id 编辑时不能为空
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<TravelAgency> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TravelAgency::getTitle, title);
        wrapper.ne(id != null, TravelAgency::getId, id);
        Long count = travelAgencyMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("旅行社名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.TRAVEL_AGENCY_REDO);
        }
    }
}
