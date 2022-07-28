package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.CouponMode;
import com.eghm.common.enums.ref.CouponState;
import com.eghm.common.enums.ref.CouponType;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.UserCouponMapper;
import com.eghm.dao.model.CouponConfig;
import com.eghm.dao.model.UserCoupon;
import com.eghm.model.dto.business.coupon.user.GrantCouponDTO;
import com.eghm.model.dto.business.coupon.user.ReceiveCouponDTO;
import com.eghm.model.dto.business.coupon.user.UserCouponQueryPageDTO;
import com.eghm.model.dto.business.coupon.user.UserCouponQueryRequest;
import com.eghm.model.vo.coupon.UserCouponBaseVO;
import com.eghm.model.vo.coupon.UserCouponResponse;
import com.eghm.model.vo.coupon.UserCouponVO;
import com.eghm.service.business.CouponConfigService;
import com.eghm.service.business.CouponProductService;
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

    private final CouponProductService couponProductService;

    @Override
    public Page<UserCouponResponse> getByPage(UserCouponQueryRequest request) {
        return userCouponMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void receiveCoupon(ReceiveCouponDTO dto) {
        dto.setMode(CouponMode.PAGE_RECEIVE);
        dto.setNum(1);
        this.doGrantCoupon(dto);
    }

    @Override
    public void grantCoupon(GrantCouponDTO dto) {
        ReceiveCouponDTO coupon = new ReceiveCouponDTO();
        coupon.setUserId(dto.getUserId());
        coupon.setMode(CouponMode.GRANT);
        for (GrantCouponDTO.GrantConfig config : dto.getConfigList()) {
            config.setCouponConfigId(config.getCouponConfigId());
            coupon.setNum(config.getNum());
            this.doGrantCoupon(coupon);
        }
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

    @Override
    public Integer getCouponAmountWithVerify(Long userId, Long couponId, Long productId, Integer amount) {
        UserCoupon coupon = userCouponMapper.selectById(couponId);
        if (coupon == null) {
            log.error("优惠券不存在 [{}]", couponId);
            throw new BusinessException(ErrorCode.COUPON_NOT_FOUND);
        }
        if (!coupon.getUserId().equals(userId)) {
            log.error("优惠券不属于该用户所有 [{}] [{}]", userId, couponId);
            throw new BusinessException(ErrorCode.COUPON_ILLEGAL);
        }

        if (coupon.getState() != CouponState.UNUSED) {
            log.error("优惠券状态非法 [{}] [{}]", coupon.getState(), couponId);
            throw new BusinessException(ErrorCode.COUPON_USE_ERROR);
        }

        boolean match = couponProductService.match(coupon.getCouponConfigId(), productId);
        if (!match) {
            log.error("商品无法匹配该优惠券 [{}] [{}]", couponId, productId);
            throw new BusinessException(ErrorCode.COUPON_MATCH);
        }
        CouponConfig config = couponConfigService.selectById(coupon.getCouponConfigId());
        LocalDateTime now = LocalDateTime.now();
        if (config.getUseStartTime().isAfter(now) || config.getUseEndTime().isBefore(now)) {
            log.error("优惠券不在有效期 [{}] [{}] [{}]", couponId, config.getStartTime(), config.getUseEndTime());
            throw new BusinessException(ErrorCode.COUPON_USE_ERROR);
        }

        if (config.getCouponType() == CouponType.DEDUCTION) {
            if (config.getDeductionValue() > amount) {
                log.error("优惠券不满足使用条件 [{}] [{}]", couponId, amount);
                throw new BusinessException(ErrorCode.COUPON_USE_THRESHOLD);
            }
            return config.getDeductionValue();
        }
        // 百分比折扣
        return amount * config.getDiscountValue() / 100;
    }

    @Override
    public void useCoupon(Long id) {
        LambdaUpdateWrapper<UserCoupon> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(UserCoupon::getId, id);
        wrapper.set(UserCoupon::getState, CouponState.USED);
        wrapper.set(UserCoupon::getUseTime, LocalDateTime.now());
        userCouponMapper.update(null, wrapper);
    }

    @Override
    public void releaseCoupon(Long id) {
        if (id == null) {
            log.info("该笔订单没有使用优惠券");
            return;
        }
        UserCoupon coupon = userCouponMapper.selectById(id);
        if (coupon.getState() != CouponState.USED) {
            log.warn("该优惠券未使用,不需要释放 [{}]", id);
            return;
        }
        CouponConfig config = couponConfigService.selectById(coupon.getCouponConfigId());
        LocalDateTime now = LocalDateTime.now();
        // 优惠券在有效期则改为未使用,否则改为已过期
        if (config.getUseStartTime().isBefore(now) && config.getUseEndTime().isAfter(now)) {
            coupon.setState(CouponState.UNUSED);
        } else {
            coupon.setState(CouponState.EXPIRE);
        }
        coupon.setUseTime(null);
        userCouponMapper.updateById(coupon);
    }

    /**
     * 发放优惠券给用户
     * @param dto 发放信息
     */
    private void doGrantCoupon(ReceiveCouponDTO dto) {
        CouponConfig config = couponConfigService.selectById(dto.getCouponConfigId());
        this.checkCoupon(config, dto);
        UserCoupon coupon = new UserCoupon();
        coupon.setUserId(dto.getUserId());
        coupon.setCouponConfigId(dto.getCouponConfigId());
        coupon.setState(CouponState.UNUSED);
        coupon.setReceiveTime(LocalDateTime.now());
        userCouponMapper.insert(coupon);
        couponConfigService.updateStock(dto.getCouponConfigId(), 1);
    }

    /**
     * 校验优惠券等信息
     * @param config 优惠券配置信息
     * @param dto  领取信息
     */
    private void checkCoupon(CouponConfig config, ReceiveCouponDTO dto) {

        if (config == null || config.getState() != 1 || (config.getStock() - dto.getNum()) <= 0) {
            log.error("领取优惠券失败, 优惠券可能库存不足 [{}]", config != null ? config.getStock() : -1);
            throw new BusinessException(ErrorCode.COUPON_EMPTY);
        }

        if (config.getMode() != dto.getMode()) {
            log.error("优惠券领取方式不匹配 [{}] [{}]", config.getId(), dto.getMode());
            throw new BusinessException(ErrorCode.COUPON_MODE_ERROR);
        }

        LocalDateTime now = LocalDateTime.now();
        if (config.getStartTime().isAfter(now) || config.getEndTime().isBefore(now)) {
            log.error("优惠券不在领取时间内 [{}] [{}] [{}]", config.getId(), config.getStartTime(), config.getEndTime());
            throw new BusinessException(ErrorCode.COUPON_INVALID_TIME);
        }

        int count = this.receiveCount(config.getId(), dto.getUserId());
        if (count >= config.getMaxLimit()) {
            log.error("优惠券领取已达上限 [{}] [{}] [{}]", dto.getUserId(), config.getId(), count);
            throw new BusinessException(ErrorCode.COUPON_MAX);
        }
    }

}
