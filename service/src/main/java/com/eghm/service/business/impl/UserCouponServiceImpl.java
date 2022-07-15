package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.CouponMode;
import com.eghm.common.enums.ref.CouponState;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.UserCouponMapper;
import com.eghm.dao.model.CouponConfig;
import com.eghm.dao.model.UserCoupon;
import com.eghm.model.dto.business.coupon.user.ReceiveCouponDTO;
import com.eghm.model.dto.business.coupon.user.UserCouponQueryPageDTO;
import com.eghm.model.vo.coupon.UserCouponBaseVO;
import com.eghm.model.vo.coupon.UserCouponVO;
import com.eghm.service.business.CouponConfigService;
import com.eghm.service.business.UserCouponService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

        this.checkCoupon(config, dto.getUserId());

        UserCoupon coupon = new UserCoupon();
        coupon.setUserId(dto.getUserId());
        coupon.setCouponConfigId(dto.getCouponConfigId());
        coupon.setState(CouponState.UNUSED);
        coupon.setReceiveTime(LocalDateTime.now());
        userCouponMapper.insert(coupon);
        couponConfigService.updateStock(dto.getCouponConfigId(), 1);
    }

    @Override
    public int receiveCount(Long couponId, Long userId) {
        LambdaQueryWrapper<UserCoupon> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserCoupon::getCouponConfigId, couponId);
        wrapper.eq(UserCoupon::getUserId, userId);
        Integer count = userCouponMapper.selectCount(wrapper);
        return count != null ? count : 0;
    }

    @Override
    public List<UserCouponVO> userCouponPage(UserCouponQueryPageDTO dto) {
        Page<UserCouponVO> voPage = userCouponMapper.userCouponPage(dto.createPage(false), dto);
        return voPage.getRecords();
    }

    @Override
    public List<UserCouponBaseVO> selectCoupon(Long userId, Long productId) {
        return userCouponMapper.selectCoupon(userId, productId);
    }

    /**
     * 校验优惠券等信息
     * @param config 优惠券配置信息
     * @param userId 用户id
     */
    private void checkCoupon(CouponConfig config, Long userId) {

        if (config == null || config.getState() != 1 || config.getStock() <= 0) {
            log.error("领取优惠券失败, 优惠券可能库存不足 [{}]", config != null ? config.getStock() : -1);
            throw new BusinessException(ErrorCode.COUPON_EMPTY);
        }

        if (config.getMode() != CouponMode.PAGE_RECEIVE) {
            log.error("优惠券不支持页面领取 [{}]", config.getId());
            throw new BusinessException(ErrorCode.COUPON_MODE_ERROR);
        }

        LocalDateTime now = LocalDateTime.now();
        if (config.getStartTime().isAfter(now) || config.getEndTime().isBefore(now)) {
            log.error("优惠券不在领取时间内 [{}] [{}] [{}]", config.getId(), config.getStartTime(), config.getEndTime());
            throw new BusinessException(ErrorCode.COUPON_INVALID_TIME);
        }

        int count = this.receiveCount(config.getId(), userId);
        if (count >= config.getMaxLimit()) {
            log.error("优惠券领取已达上限 [{}] [{}] [{}]", userId, config.getId(), count);
            throw new BusinessException(ErrorCode.COUPON_MAX);
        }
    }

}
