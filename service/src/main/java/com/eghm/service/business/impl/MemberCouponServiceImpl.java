package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.coupon.member.GrantCouponDTO;
import com.eghm.dto.business.coupon.member.MemberCouponQueryPageDTO;
import com.eghm.dto.business.coupon.member.MemberCouponQueryRequest;
import com.eghm.dto.business.coupon.member.ReceiveCouponDTO;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.CouponMode;
import com.eghm.enums.ref.CouponState;
import com.eghm.enums.ref.CouponType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.CouponMapper;
import com.eghm.mapper.MemberCouponMapper;
import com.eghm.model.Coupon;
import com.eghm.model.MemberCoupon;
import com.eghm.service.business.CouponScopeService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.vo.business.coupon.MemberCouponBaseVO;
import com.eghm.vo.business.coupon.MemberCouponCountVO;
import com.eghm.vo.business.coupon.MemberCouponResponse;
import com.eghm.vo.business.coupon.MemberCouponVO;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2022/7/13
 */
@Service("memberCouponService")
@AllArgsConstructor
@Slf4j
public class MemberCouponServiceImpl implements MemberCouponService {

    private final MemberCouponMapper memberCouponMapper;

    private final CouponMapper couponMapper;

    private final CouponScopeService couponScopeService;

    @Override
    public Page<MemberCouponResponse> getByPage(MemberCouponQueryRequest request) {
        return memberCouponMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void receiveCoupon(ReceiveCouponDTO dto) {
        dto.setMode(CouponMode.PAGE_RECEIVE);
        dto.setNum(1);
        this.doGrantCoupon(dto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.SUPPORTS)
    public void grantCoupon(GrantCouponDTO dto) {
        ReceiveCouponDTO coupon = new ReceiveCouponDTO();
        coupon.setCouponId(dto.getCouponId());
        coupon.setNum(dto.getNum());
        coupon.setMode(CouponMode.GRANT);
        for (Long memberId : dto.getMemberIdList()) {
            coupon.setMemberId(memberId);
            this.doGrantCoupon(coupon);
        }
    }

    @Override
    public int receiveCount(Long couponId, Long memberId) {
        LambdaQueryWrapper<MemberCoupon> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MemberCoupon::getCouponId, couponId);
        wrapper.eq(MemberCoupon::getMemberId, memberId);
        Long count = memberCouponMapper.selectCount(wrapper);
        return count != null ? count.intValue() : 0;
    }

    @Override
    public List<MemberCouponVO> memberCouponPage(MemberCouponQueryPageDTO dto) {
        Page<MemberCouponVO> voPage = memberCouponMapper.memberCouponPage(dto.createPage(false), dto);
        return voPage.getRecords();
    }

    @Override
    public List<MemberCouponBaseVO> selectCoupon(Long memberId, Long itemId) {
        return memberCouponMapper.selectCoupon(memberId, itemId);
    }

    @Override
    public Integer getCouponAmountWithVerify(Long memberId, Long couponId, Long productId, Integer amount) {
        MemberCoupon coupon = memberCouponMapper.selectById(couponId);
        if (coupon == null) {
            log.error("优惠券不存在 [{}]", couponId);
            throw new BusinessException(ErrorCode.COUPON_NOT_FOUND);
        }
        if (!coupon.getMemberId().equals(memberId)) {
            log.error("优惠券不属于该用户所有 [{}] [{}]", memberId, couponId);
            throw new BusinessException(ErrorCode.COUPON_ILLEGAL);
        }

        if (coupon.getState() != CouponState.UNUSED) {
            log.error("优惠券状态非法 [{}] [{}]", coupon.getState(), couponId);
            throw new BusinessException(ErrorCode.COUPON_USE_ERROR);
        }

        boolean match = couponScopeService.match(coupon.getCouponId(), productId);
        if (!match) {
            log.error("商品无法匹配该优惠券 [{}] [{}]", couponId, productId);
            throw new BusinessException(ErrorCode.COUPON_MATCH);
        }
        Coupon config = couponMapper.selectById(coupon.getCouponId());
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
        LambdaUpdateWrapper<MemberCoupon> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MemberCoupon::getId, id);
        wrapper.set(MemberCoupon::getState, CouponState.USED);
        wrapper.set(MemberCoupon::getUseTime, LocalDateTime.now());
        memberCouponMapper.update(null, wrapper);
    }

    @Override
    public void releaseCoupon(Long id) {
        if (id == null) {
            log.info("该笔订单没有使用优惠券");
            return;
        }
        MemberCoupon coupon = memberCouponMapper.selectById(id);
        if (coupon == null || coupon.getState() != CouponState.USED) {
            log.warn("该优惠券未使用,不需要释放 [{}]", id);
            return;
        }
        Coupon config = couponMapper.selectById(coupon.getCouponId());
        LocalDateTime now = LocalDateTime.now();
        // 优惠券在有效期则改为未使用,否则改为已过期
        if (config.getUseStartTime().isBefore(now) && config.getUseEndTime().isAfter(now)) {
            coupon.setState(CouponState.UNUSED);
        } else {
            coupon.setState(CouponState.EXPIRE);
        }
        coupon.setUseTime(null);
        memberCouponMapper.updateById(coupon);
    }

    @Override
    public Map<Long, Integer> countMemberReceived(Long memberId, List<Long> couponIds) {
        if (CollUtil.isEmpty(couponIds)) {
            return Maps.newLinkedHashMapWithExpectedSize(1);
        }
        List<MemberCouponCountVO> receivedList = memberCouponMapper.countMemberReceived(memberId, couponIds);
        return receivedList.stream().collect(Collectors.toMap(MemberCouponCountVO::getCouponId, MemberCouponCountVO::getNum));
    }

    @Override
    public void couponExpire(Long couponId) {
        memberCouponMapper.couponExpire(couponId);
    }

    /**
     * 发放优惠券给用户
     *
     * @param dto 发放信息
     */
    private void doGrantCoupon(ReceiveCouponDTO dto) {
        Coupon config = couponMapper.selectById(dto.getCouponId());
        this.checkCoupon(config, dto);
        MemberCoupon coupon = new MemberCoupon();
        coupon.setMemberId(dto.getMemberId());
        coupon.setCouponId(dto.getCouponId());
        coupon.setState(CouponState.UNUSED);
        coupon.setReceiveTime(LocalDateTime.now());
        memberCouponMapper.insert(coupon);
        int stock = couponMapper.updateStock(dto.getCouponId(), -1);
        if (stock != 1) {
            log.error("优惠券库存更新异常 [{}]", dto.getCouponId());
            throw new BusinessException(ErrorCode.COUPON_EMPTY);
        }
    }

    /**
     * 校验优惠券等信息
     *
     * @param config 优惠券配置信息
     * @param dto    领取信息
     */
    private void checkCoupon(Coupon config, ReceiveCouponDTO dto) {

        if (config == null || config.getState() != 1 || (config.getStock() - dto.getNum()) <= 0) {
            log.error("领取优惠券失败, 优惠券可能库存不足 [{}] [{}]", config != null ? config.getState() : -1, config != null ? config.getStock() : -1);
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

        int count = this.receiveCount(config.getId(), dto.getMemberId());
        if (count + dto.getNum() > config.getMaxLimit()) {
            log.error("已领取+待发放数量优惠券总和超过上限 [{}] [{}] [{}] [{}] [{}]", dto.getMemberId(), config.getId(), count, dto.getNum(), config.getMaxLimit());
            throw new BusinessException(ErrorCode.COUPON_GET_MAX);
        }
    }

}
