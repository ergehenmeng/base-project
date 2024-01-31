package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.venue.VenueSessionAddRequest;
import com.eghm.dto.business.venue.VenueSessionEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.VenueSessionMapper;
import com.eghm.model.VenueSession;
import com.eghm.service.business.VenueSessionPriceService;
import com.eghm.service.business.VenueSessionService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 场馆场次价格信息表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
@Slf4j
@AllArgsConstructor
@Service("venueSessionService")
public class VenueSessionServiceImpl implements VenueSessionService {

    private final VenueSessionMapper venueSessionMapper;

    private final VenueSessionPriceService venueSessionPriceService;

    @Override
    public void create(VenueSessionAddRequest request) {
        Long merchantId = SecurityHolder.getMerchantId();
        this.redoTitle(request.getTitle(), merchantId, request.getVenueId(), null);
        VenueSession session = DataUtil.copy(request, VenueSession.class);
        session.setMerchantId(merchantId);
        venueSessionMapper.insert(session);
        venueSessionPriceService.insertOrUpdate(request.getPriceList(), session);
    }

    @Override
    public void update(VenueSessionEditRequest request) {
        Long merchantId = SecurityHolder.getMerchantId();
        this.redoTitle(request.getTitle(), merchantId, request.getVenueId(), request.getId());
        VenueSession session = DataUtil.copy(request, VenueSession.class);
        session.setMerchantId(merchantId);
        venueSessionMapper.updateById(session);
        venueSessionPriceService.insertOrUpdate(request.getPriceList(), session);
    }

    @Override
    public void delete(Long id) {
        LambdaUpdateWrapper<VenueSession> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(VenueSession::getId, id);
        wrapper.set(VenueSession::getDeleted, true);
        wrapper.eq(VenueSession::getMerchantId, SecurityHolder.getMerchantId());
        venueSessionMapper.update(null, wrapper);
        venueSessionPriceService.delete(id);
    }

    /**
     * 校验场地名称是否重复
     *
     * @param title 场地名称
     * @param merchantId 所属商户
     * @param venueId  所属场馆id
     * @param id    id 编辑时不能为空
     */
    private void redoTitle(String title, Long merchantId, Long venueId, Long id) {
        LambdaQueryWrapper<VenueSession> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(VenueSession::getTitle, title);
        wrapper.eq(VenueSession::getVenueId, venueId);
        wrapper.eq(VenueSession::getMerchantId, merchantId);
        wrapper.ne(id != null, VenueSession::getId, id);
        Long count = venueSessionMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("场次名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.VENUE_SESSION_REDO);
        }
    }
}
