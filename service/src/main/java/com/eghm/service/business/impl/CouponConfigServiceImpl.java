package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.CouponMode;
import com.eghm.common.exception.BusinessException;
import com.eghm.mapper.CouponConfigMapper;
import com.eghm.model.CouponConfig;
import com.eghm.model.dto.business.coupon.config.CouponConfigAddRequest;
import com.eghm.model.dto.business.coupon.config.CouponConfigEditRequest;
import com.eghm.model.dto.business.coupon.config.CouponConfigQueryRequest;
import com.eghm.service.business.CouponConfigService;
import com.eghm.service.business.CouponProductService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Service("couponConfigService")
@AllArgsConstructor
@Slf4j
public class CouponConfigServiceImpl implements CouponConfigService {

    private final CouponConfigMapper couponConfigMapper;

    private final CouponProductService couponProductService;

    @Override
    public Page<CouponConfig> getByPage(CouponConfigQueryRequest request) {
        LambdaQueryWrapper<CouponConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), CouponConfig::getTitle, request.getQueryName());
        wrapper.gt(request.getState() == 0, CouponConfig::getStartTime, LocalDateTime.now());
        wrapper.and(request.getState() == 1, queryWrapper -> {
            LocalDateTime now = LocalDateTime.now();
            queryWrapper.ge(CouponConfig::getStartTime, now);
            queryWrapper.le(CouponConfig::getEndTime, now);
        });
        wrapper.lt(request.getState() == 2, CouponConfig::getEndTime, LocalDateTime.now());
        wrapper.gt(Boolean.TRUE.equals(request.getInStock()), CouponConfig::getStock, 0);
        wrapper.eq(request.getMode() != null, CouponConfig::getMode, CouponMode.valueOf(request.getMode()));

        wrapper.last(" order by state desc, id desc ");
        return couponConfigMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(CouponConfigAddRequest request) {
        CouponConfig config = DataUtil.copy(request, CouponConfig.class);
        couponConfigMapper.insert(config);
        couponProductService.insert(config.getId(), request.getProductList());
    }

    @Override
    public void update(CouponConfigEditRequest request) {
        CouponConfig config = DataUtil.copy(request, CouponConfig.class);
        couponConfigMapper.updateById(config);
        couponProductService.insertWithDelete(config.getId(), request.getProductList());
    }

    @Override
    public void updateState(Long id, Integer state) {
        LambdaUpdateWrapper<CouponConfig> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(CouponConfig::getId, id);
        wrapper.set(CouponConfig::getState, state);
        couponConfigMapper.update(null, wrapper);
    }

    @Override
    public CouponConfig selectById(Long id) {
        return couponConfigMapper.selectById(id);
    }

    @Override
    public void updateStock(Long id, int num) {
        int stock = couponConfigMapper.updateStock(id, num);
        if (stock != 1) {
            log.error("优惠券库存更新异常 [{}] [{}]", id, num);
            throw new BusinessException(ErrorCode.COUPON_EMPTY);
        }
    }
}
