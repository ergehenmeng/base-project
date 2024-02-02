package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.venue.PriceRequest;
import com.eghm.dto.business.venue.VenueSitePriceRequest;
import com.eghm.dto.business.venue.VenueSitePriceUpdateRequest;
import com.eghm.mapper.VenueSitePriceMapper;
import com.eghm.model.VenueSitePrice;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.VenueSitePriceService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
@Service("venueSitePriceService")
public class VenueSitePriceServiceImpl extends ServiceImpl<VenueSitePriceMapper, VenueSitePrice> implements VenueSitePriceService {

    private final CommonService commonService;

    @Override
    public void insertOrUpdate(VenueSitePriceRequest request) {
        long between = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        commonService.checkMaxDay(ConfigConstant.SITE_CONFIG_MAX_DAY, between);

        LambdaUpdateWrapper<VenueSitePrice> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(VenueSitePrice::getVenueId, request.getVenueId());
        wrapper.eq(VenueSitePrice::getVenueSiteId, request.getVenueSiteId());
        wrapper.eq(VenueSitePrice::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.between(VenueSitePrice::getNowDate, request.getStartDate(), request.getEndDate());
        wrapper.in(VenueSitePrice::getNowWeek, request.getWeek());
        baseMapper.delete(wrapper);

        LocalDate startDate = request.getStartDate();
        for (int i = 0; i <= between; i++) {
            LocalDate nowDate = startDate.plusDays(i);
            int nowWeek = nowDate.getDayOfWeek().getValue();
            if (!request.getWeek().contains(nowWeek)) {
                continue;
            }

            List<VenueSitePrice> priceList = new ArrayList<>();
            for (PriceRequest priceRequest : request.getPriceList()) {
                VenueSitePrice price = DataUtil.copy(priceRequest, VenueSitePrice.class);
                price.setVenueId(request.getVenueId());
                price.setVenueSiteId(request.getVenueSiteId());
                price.setNowDate(nowDate);
                price.setMerchantId(SecurityHolder.getMerchantId());
                price.setNowWeek(nowWeek);
                priceList.add(price);
            }
            VenueSitePriceService service = (VenueSitePriceService) AopContext.currentProxy();
            service.saveBatch(priceList);
        }
    }

    @Override
    public void update(VenueSitePriceUpdateRequest request) {
        LambdaUpdateWrapper<VenueSitePrice> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(VenueSitePrice::getId, request.getId());
        wrapper.eq(VenueSitePrice::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.set(VenueSitePrice::getPrice, request.getPrice());
        wrapper.set(VenueSitePrice::getState, request.getState());
        baseMapper.update(null, wrapper);
    }

    @Override
    public void delete(Long id) {
        LambdaUpdateWrapper<VenueSitePrice> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(VenueSitePrice::getId, id);
        wrapper.eq(VenueSitePrice::getMerchantId, SecurityHolder.getMerchantId());
        baseMapper.delete(wrapper);
    }
}
