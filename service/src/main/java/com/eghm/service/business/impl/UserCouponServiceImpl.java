package com.eghm.service.business.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.UserCouponMapper;
import com.eghm.dao.model.CouponConfig;
import com.eghm.model.dto.business.coupon.user.ReceiveCouponDTO;
import com.eghm.service.business.CouponConfigService;
import com.eghm.service.business.UserCouponService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Service("userCouponService")
@AllArgsConstructor
@Slf4j
public class UserCouponServiceImpl implements UserCouponService {

    private final UserCouponMapper userCouponMapper;

    private final CouponConfigService couponConfigService;

    @Override
    public void receiveCoupon(ReceiveCouponDTO dto) {
        CouponConfig config = couponConfigService.selectById(dto.getCouponConfigId());
        if (config == null || config.getState() != 1 || config.getStock() <= 0) {
            log.error("领取优惠券失败, 优惠券可能库存不足 [{}] [{}]", dto.getCouponConfigId(), config != null ? config.getStock() : -1);
            throw new BusinessException(ErrorCode.COUPON_EMPTY);
        }
        LocalDateTime now = LocalDateTime.now();
        if (config.getStartTime().isAfter(now) || config.getEndTime().isBefore(now)) {
            log.error("优惠券不在领取时间内 [{}] [{}] [{}]", config.getId(), config.getStartTime(), config.getEndTime());
            throw new BusinessException(ErrorCode.COUPON_INVALID_TIME);
        }

    }
}
