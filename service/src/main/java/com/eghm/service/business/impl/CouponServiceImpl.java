package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.coupon.config.CouponAddRequest;
import com.eghm.dto.business.coupon.config.CouponEditRequest;
import com.eghm.dto.business.coupon.config.CouponQueryDTO;
import com.eghm.dto.business.coupon.config.CouponQueryRequest;
import com.eghm.dto.business.coupon.product.CouponProductDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.CouponMapper;
import com.eghm.model.Coupon;
import com.eghm.mq.service.MessageService;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.CouponScopeService;
import com.eghm.service.business.CouponService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.coupon.CouponDetailResponse;
import com.eghm.vo.business.coupon.CouponResponse;
import com.eghm.vo.business.coupon.CouponVO;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.eghm.enums.ErrorCode.*;

/**
 * @author 二哥很猛
 * @since 2022/7/13
 */
@Service("couponService")
@AllArgsConstructor
@Slf4j
public class CouponServiceImpl implements CouponService {

    private final CouponMapper couponMapper;

    private final CouponScopeService couponScopeService;

    private final MemberCouponService memberCouponService;

    private final CommonService commonService;

    private final MessageService messageService;

    @Override
    public Page<CouponResponse> getByPage(CouponQueryRequest request) {
        return couponMapper.listPage(request.createPage(), request);
    }

    @Override
    public void create(CouponAddRequest request) {
        this.checkScope(request.getUseScope(), request.getProductIds());
        Coupon config = DataUtil.copy(request, Coupon.class);
        couponMapper.insert(config);
        couponScopeService.insertOnUpdate(config.getId(), request.getProductIds(), request.getProductType());
        // 注意: 如果优惠券过期时间太大,则long转int时可能会溢出
        long expireTime = ChronoUnit.SECONDS.between(LocalDateTime.now(), request.getUseEndTime());
        messageService.sendDelay(ExchangeQueue.COUPON_EXPIRE, config.getId(), (int) expireTime);
    }

    @Override
    public void update(CouponEditRequest request) {
        Coupon coupon = this.selectByIdRequired(request.getId());
        commonService.checkIllegal(coupon.getMerchantId());
        this.checkScope(coupon.getUseScope(), request.getProductIds());
        Coupon config = DataUtil.copy(request, Coupon.class);
        couponMapper.updateById(config);
        couponScopeService.insertOnUpdate(config.getId(), request.getProductIds(), request.getProductType());
    }

    @Override
    public void updateState(Long id, Integer state) {
        Coupon coupon = this.selectByIdRequired(id);
        commonService.checkIllegal(coupon.getMerchantId());
        coupon.setState(state);
        couponMapper.updateById(coupon);
    }

    @Override
    public Coupon selectById(Long id) {
        return couponMapper.selectById(id);
    }

    @Override
    public Coupon selectByIdRequired(Long id) {
        Coupon config = this.selectById(id);
        if (config == null) {
            log.error("优惠券不存在 [{}]", id);
            throw new BusinessException(ErrorCode.COUPON_NOT_FOUND);
        }
        return config;
    }

    @Override
    public CouponDetailResponse getById(Long id) {
        Coupon coupon = this.selectByIdRequired(id);
        CouponDetailResponse response = DataUtil.copy(coupon, CouponDetailResponse.class);
        if (coupon.getUseScope() == 1) {
            response.setProductIds(Lists.newArrayList());
        } else {
            List<Long> productIds = couponScopeService.getProductIds(id);
            response.setProductIds(productIds);
        }
        return response;
    }

    @Override
    public List<CouponVO> getByPage(CouponQueryDTO dto) {
        Page<CouponVO> voPage = couponMapper.getByPage(dto.createPage(false), dto);
        List<CouponVO> voList = voPage.getRecords();
        this.fillAttribute(voList);
        return voList;
    }

    @Override
    public List<CouponVO> getProductCoupon(CouponProductDTO dto) {
        Long storeId = commonService.getStoreId(dto.getProductId(), dto.getProductType());
        // 优惠券有店铺券或商品券之分
        List<CouponVO> couponList = couponMapper.getProductCoupon(dto.getProductId(), storeId);
        this.fillAttribute(couponList);
        return couponList;
    }

    @Override
    public CouponVO getDetail(Long id) {
        CouponVO detail = couponMapper.getDetail(id);
        if (detail == null) {
            log.error("优惠券不存在 [{}]", id);
            throw new BusinessException(COUPON_NULL);
        }
        Long memberId = ApiHolder.tryGetMemberId();
        if (memberId == null) {
            return detail;
        }
        Map<Long, Integer> receivedMap = memberCouponService.countMemberReceived(memberId, Lists.newArrayList(detail.getId()));
        detail.setReceived(receivedMap.getOrDefault(detail.getId(), 0) >= detail.getMaxLimit());
        return detail;
    }

    /**
     * 检查优惠券使用范围
     *
     * @param useScope 使用范围 1:店铺通用 2:指定商品
     * @param productIds 商品id
     */
    private void checkScope(Integer useScope, List<Long> productIds) {
        if (useScope == 1 && CollUtil.isNotEmpty(productIds)) {
            throw new BusinessException(COUPON_SCOPE_ILLEGAL);
        }
        if (useScope == 2 && CollUtil.isEmpty(productIds)) {
            throw new BusinessException(COUPON_SCOPE_NULL);
        }
    }

    /**
     * 填充优惠券是否已领取字段属性
     *
     * @param couponList 优惠券信息
     */
    private void fillAttribute(List<CouponVO> couponList) {
        Long memberId = ApiHolder.tryGetMemberId();
        // 用户未登陆, 默认全部可以领取
        if (memberId == null || CollUtil.isEmpty(couponList)) {
            return;
        }
        List<Long> couponIds = couponList.stream().map(CouponVO::getId).collect(Collectors.toList());

        Map<Long, Integer> receivedMap = memberCouponService.countMemberReceived(memberId, couponIds);
        couponList.forEach(vo -> vo.setReceived(receivedMap.getOrDefault(vo.getId(), 0) >= vo.getMaxLimit()));
    }
}
