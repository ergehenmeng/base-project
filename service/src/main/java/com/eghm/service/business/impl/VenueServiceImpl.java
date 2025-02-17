package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.GeoService;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.venue.VenueAddRequest;
import com.eghm.dto.business.venue.VenueEditRequest;
import com.eghm.dto.business.venue.VenueQueryDTO;
import com.eghm.dto.business.venue.VenueQueryRequest;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderEvaluationMapper;
import com.eghm.mapper.VenueMapper;
import com.eghm.mapper.VenueSiteMapper;
import com.eghm.model.Venue;
import com.eghm.model.VenueSite;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.VenueService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.evaluation.AvgScoreVO;
import com.eghm.vo.business.venue.BaseVenueResponse;
import com.eghm.vo.business.venue.VenueDetailVO;
import com.eghm.vo.business.venue.VenueResponse;
import com.eghm.vo.business.venue.VenueVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 场馆信息 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
@Slf4j
@AllArgsConstructor
@Service("venueService")
public class VenueServiceImpl implements VenueService {

    private final GeoService geoService;

    private final VenueMapper venueMapper;

    private final CommonService commonService;

    private final SysAreaService sysAreaService;

    private final VenueSiteMapper venueSiteMapper;

    private final OrderEvaluationMapper orderEvaluationMapper;

    @Override
    public Page<VenueResponse> listPage(VenueQueryRequest request) {
        Page<VenueResponse> listPage = venueMapper.listPage(request.createPage(), request);
        if (CollUtil.isNotEmpty(listPage.getRecords())) {
            this.transfer(listPage.getRecords());
        }
        return listPage;
    }

    @Override
    public List<VenueResponse> getList(VenueQueryRequest request) {
        Page<VenueResponse> listPage = venueMapper.listPage(request.createNullPage(), request);
        if (CollUtil.isNotEmpty(listPage.getRecords())) {
            this.transfer(listPage.getRecords());
        }
        return listPage.getRecords();
    }

    @Override
    public List<BaseVenueResponse> getList(Long merchantId) {
        return venueMapper.getList(merchantId);
    }

    @Override
    public void create(VenueAddRequest request) {
        Long merchantId = SecurityHolder.getMerchantId();
        this.redoTitle(request.getTitle(), merchantId, null);
        Venue venue = DataUtil.copy(request, Venue.class);
        venue.setMerchantId(merchantId);
        venue.setState(State.UN_SHELVE);
        venue.setCoverUrl(CollUtil.join(request.getCoverList(), CommonConstant.COMMA));
        venueMapper.insert(venue);
        geoService.addPoint(CacheConstant.GEO_POINT_VENUE, venue.getId().toString(), request.getLongitude().doubleValue(), request.getLatitude().doubleValue());
    }

    @Override
    public void update(VenueEditRequest request) {
        Long merchantId = SecurityHolder.getMerchantId();
        this.redoTitle(request.getTitle(), merchantId, request.getId());
        Venue required = this.selectByIdRequired(request.getId());
        commonService.checkIllegal(required.getMerchantId());
        Venue venue = DataUtil.copy(request, Venue.class);
        venue.setCoverUrl(CollUtil.join(request.getCoverList(), CommonConstant.COMMA));
        venueMapper.updateById(venue);
        geoService.addPoint(CacheConstant.GEO_POINT_VENUE, venue.getId().toString(), request.getLongitude().doubleValue(), request.getLatitude().doubleValue());
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Venue> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Venue::getId, id);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, Venue::getMerchantId, merchantId);
        wrapper.set(Venue::getState, state);
        venueMapper.update(null, wrapper);
    }

    @Override
    public void delete(Long id) {
        LambdaUpdateWrapper<Venue> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Venue::getId, id);
        wrapper.eq(Venue::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.set(Venue::getState, State.UN_SHELVE);
        wrapper.set(Venue::getDeleted, true);
        venueMapper.update(null, wrapper);
    }

    @Override
    public Venue selectByIdRequired(Long id) {
        Venue venue = venueMapper.selectById(id);
        if (venue == null) {
            log.info("不存在的场馆信息 [{}]", id);
            throw new BusinessException(ErrorCode.VENUE_NULL);
        }
        return venue;
    }

    @Override
    public Venue selectByIdShelve(Long id) {
        Venue venue = venueMapper.selectById(id);
        if (venue == null || venue.getState() != State.SHELVE) {
            log.info("场馆信息信息已下架 [{}]", id);
            throw new BusinessException(ErrorCode.VENUE_DOWN);
        }
        return venue;
    }

    @Override
    public List<VenueVO> getByPage(VenueQueryDTO dto) {
        Page<VenueVO> byPage = venueMapper.getByPage(dto.createPage(false), dto);
        return byPage.getRecords();
    }

    @Override
    public VenueDetailVO getDetail(Long id) {
        Venue venue = this.selectByIdShelve(id);
        return DataUtil.copy(venue, VenueDetailVO.class);
    }

    @Override
    public Page<BaseStoreResponse> getStorePage(BaseStoreQueryRequest request) {
        return venueMapper.getStorePage(Boolean.TRUE.equals(request.getLimit()) ? request.createPage() : request.createNullPage(), request);
    }

    @Override
    public void logout(Long merchantId) {
        LambdaUpdateWrapper<Venue> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Venue::getMerchantId, merchantId);
        wrapper.set(Venue::getState, State.FORCE_UN_SHELVE);
        venueMapper.update(null, wrapper);
        LambdaUpdateWrapper<VenueSite> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(VenueSite::getMerchantId, merchantId);
        updateWrapper.set(VenueSite::getState, State.FORCE_UN_SHELVE);
        venueSiteMapper.update(null, updateWrapper);
    }

    @Override
    public void updateScore(CalcStatistics vo) {
        AvgScoreVO storeScore = orderEvaluationMapper.getStoreScore(vo.getStoreId());
        if (storeScore.getNum() < CommonConstant.MIN_SCORE_NUM) {
            log.info("为保证评分系统的公平性, 评价数量小于5条时默认不展示餐饮商家评分 [{}]", vo.getStoreId());
            return;
        }
        venueMapper.updateScore(vo.getStoreId(), DecimalUtil.calcAvgScore(storeScore.getTotalScore(), storeScore.getNum()));
    }

    /**
     * 市县格式化
     *
     * @param venueList 场馆信息
     */
    private void transfer(List<VenueResponse> venueList) {
        venueList.forEach(venue -> venue.setDetailAddress(sysAreaService.parseArea(venue.getCityId(), venue.getCountyId(), venue.getDetailAddress())));
    }

    /**
     * 校验场馆名称是否重复
     *
     * @param title 场馆名称
     * @param merchantId 所属商户
     * @param id    id 编辑时不能为空
     */
    private void redoTitle(String title, Long merchantId, Long id) {
        LambdaQueryWrapper<Venue> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Venue::getTitle, title);
        wrapper.eq(Venue::getMerchantId, merchantId);
        wrapper.ne(id != null, Venue::getId, id);
        Long count = venueMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("场馆名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.VENUE_REDO);
        }
    }
}
