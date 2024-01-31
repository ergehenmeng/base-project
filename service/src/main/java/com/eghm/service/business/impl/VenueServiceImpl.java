package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.venue.VenueAddRequest;
import com.eghm.dto.business.venue.VenueEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.VenueMapper;
import com.eghm.model.Venue;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.VenueService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private final VenueMapper venueMapper;

    private final CommonService commonService;

    @Override
    public void create(VenueAddRequest request) {
        Long merchantId = SecurityHolder.getMerchantId();
        this.redoTitle(request.getTitle(), merchantId, null);
        Venue venue = DataUtil.copy(request, Venue.class);
        venueMapper.insert(venue);
    }

    @Override
    public void update(VenueEditRequest request) {
        Long merchantId = SecurityHolder.getMerchantId();
        this.redoTitle(request.getTitle(), merchantId, request.getId());
        Venue required = this.selectByIdRequired(request.getId());
        commonService.checkIllegal(required.getMerchantId());

        Venue venue = DataUtil.copy(request, Venue.class);
        venueMapper.updateById(venue);
    }

    @Override
    public void delete(String id) {
        LambdaUpdateWrapper<Venue> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Venue::getId, id);
        wrapper.set(Venue::getState, State.UN_SHELVE);
        wrapper.set(Venue::getDeleted, true);
        wrapper.eq(Venue::getMerchantId, SecurityHolder.getMerchantId());
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
