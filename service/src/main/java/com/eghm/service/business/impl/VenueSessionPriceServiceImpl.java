package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.venue.SessionPriceRequest;
import com.eghm.mapper.VenueSessionPriceMapper;
import com.eghm.model.VenueSession;
import com.eghm.model.VenueSessionPrice;
import com.eghm.service.business.VenueSessionPriceService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
@Service("venueSessionPriceService")
public class VenueSessionPriceServiceImpl implements VenueSessionPriceService {

    private VenueSessionPriceMapper venueSessionPriceMapper;

    @Override
    public void insertOrUpdate(List<SessionPriceRequest> priceList, VenueSession session) {
        LambdaUpdateWrapper<VenueSessionPrice> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(VenueSessionPrice::getVenueSessionId, session.getId());
        wrapper.eq(VenueSessionPrice::getVenueId, session.getVenueId());
        wrapper.eq(VenueSessionPrice::getMerchantId, session.getMerchantId());
        venueSessionPriceMapper.delete(wrapper);

        priceList.forEach(price -> {
            VenueSessionPrice sessionPrice = DataUtil.copy(price, VenueSessionPrice.class);
            sessionPrice.setVenueSessionId(session.getId());
            sessionPrice.setVenueId(session.getVenueId());
            sessionPrice.setMerchantId(session.getMerchantId());
            venueSessionPriceMapper.insert(sessionPrice);
        });
    }

    @Override
    public void delete(Long venueSessionId) {
        LambdaUpdateWrapper<VenueSessionPrice> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(VenueSessionPrice::getVenueSessionId, venueSessionId);
        wrapper.eq(VenueSessionPrice::getMerchantId, SecurityHolder.getMerchantId());
        venueSessionPriceMapper.delete(wrapper);
    }
}
