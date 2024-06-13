package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.venue.VenueSiteAddRequest;
import com.eghm.dto.business.venue.VenueSiteEditRequest;
import com.eghm.dto.business.venue.VenueSiteQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.VenueSiteMapper;
import com.eghm.model.VenueSite;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.VenueSitePriceService;
import com.eghm.service.business.VenueSiteService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.venue.VenueSiteDetailResponse;
import com.eghm.vo.business.venue.VenueSitePriceVO;
import com.eghm.vo.business.venue.VenueSiteResponse;
import com.eghm.vo.business.venue.VenueSiteVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 场地信息表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */

@Slf4j
@AllArgsConstructor
@Service("venueSiteService")
public class VenueSiteServiceImpl implements VenueSiteService {

    private final CommonService commonService;

    private final VenueSiteMapper venueSiteMapper;

    private final VenueSitePriceService venueSitePriceService;

    @Override
    public Page<VenueSiteResponse> getByPage(VenueSiteQueryRequest request) {
        return venueSiteMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(VenueSiteAddRequest request) {
        this.redoTitle(request.getTitle(), request.getVenueId(), null);
        VenueSite venueSite = DataUtil.copy(request, VenueSite.class);
        venueSite.setMerchantId(SecurityHolder.getMerchantId());
        venueSite.setCoverUrl(CollUtil.join(request.getCoverList(), CommonConstant.COMMA));
        venueSiteMapper.insert(venueSite);
    }

    @Override
    public void update(VenueSiteEditRequest request) {
        this.redoTitle(request.getTitle(), request.getVenueId(), request.getId());
        VenueSite required = this.selectByIdRequired(request.getId());
        commonService.checkIllegal(required.getMerchantId());
        VenueSite venueSite = DataUtil.copy(request, VenueSite.class);
        venueSite.setCoverUrl(CollUtil.join(request.getCoverList(), CommonConstant.COMMA));
        venueSiteMapper.updateById(venueSite);
    }

    @Override
    public void sortBy(Long id, Integer sortBy) {
        LambdaUpdateWrapper<VenueSite> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(VenueSite::getId, id);
        wrapper.eq(VenueSite::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.set(VenueSite::getSort, sortBy);
        venueSiteMapper.update(null, wrapper);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<VenueSite> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(VenueSite::getId, id);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, VenueSite::getMerchantId, merchantId);
        wrapper.set(VenueSite::getState, state);
        venueSiteMapper.update(null, wrapper);
    }

    @Override
    public void delete(Long id) {
        LambdaUpdateWrapper<VenueSite> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(VenueSite::getId, id);
        wrapper.eq(VenueSite::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.set(VenueSite::getState, State.UN_SHELVE);
        wrapper.set(VenueSite::getDeleted, true);
        venueSiteMapper.update(null, wrapper);
    }

    @Override
    public VenueSite selectByIdRequired(Long id) {
        VenueSite venueSite = venueSiteMapper.selectById(id);
        if (venueSite == null) {
            log.info("不存在的场地信息 [{}]", id);
            throw new BusinessException(ErrorCode.VENUE_SITE_NULL);
        }
        return venueSite;
    }

    @Override
    public VenueSiteDetailResponse getDetail(Long id) {
        VenueSite venueSite = this.selectByIdRequired(id);
        VenueSiteDetailResponse response = DataUtil.copy(venueSite, VenueSiteDetailResponse.class);
        List<VenueSitePriceVO> priceList = venueSitePriceService.getPriceList(id, LocalDate.now());
        response.setPriceList(priceList);
        return response;
    }

    @Override
    public VenueSite selectByIdShelve(Long id) {
        VenueSite venueSite = venueSiteMapper.selectById(id);
        if (venueSite == null || venueSite.getState() != State.SHELVE) {
            log.info("场次信息信息已下架 [{}]", id);
            throw new BusinessException(ErrorCode.VENUE_SITE_DOWN);
        }
        return venueSite;
    }

    @Override
    public List<VenueSiteVO> getList(Long venueId) {
        LambdaQueryWrapper<VenueSite> wrapper = Wrappers.lambdaQuery();
        wrapper.select(VenueSite::getId, VenueSite::getTitle);
        wrapper.eq(VenueSite::getVenueId, venueId);
        wrapper.eq(VenueSite::getState, State.SHELVE);
        wrapper.orderByDesc(VenueSite::getSort);
        List<VenueSite> list = venueSiteMapper.selectList(wrapper);
        return DataUtil.copy(list, VenueSiteVO.class);
    }

    @Override
    public Page<BaseProductResponse> getProductPage(BaseProductQueryRequest request) {
        return venueSiteMapper.getProductPage(request.createPage(), request);
    }

    /**
     * 校验场地名称是否重复
     *
     * @param title 场地名称
     * @param venueId  所属场馆id
     * @param id    id 编辑时不能为空
     */
    private void redoTitle(String title, Long venueId, Long id) {
        LambdaQueryWrapper<VenueSite> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(VenueSite::getTitle, title);
        wrapper.eq(VenueSite::getVenueId, venueId);
        wrapper.ne(id != null, VenueSite::getId, id);
        Long count = venueSiteMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("场地名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.VENUE_SITE_REDO);
        }
    }
}
