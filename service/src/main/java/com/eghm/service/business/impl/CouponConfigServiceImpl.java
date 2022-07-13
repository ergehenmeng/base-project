package com.eghm.service.business.impl;

import com.eghm.dao.mapper.CouponConfigMapper;
import com.eghm.dao.model.CouponConfig;
import com.eghm.model.dto.business.coupon.config.CouponConfigAddRequest;
import com.eghm.model.dto.business.coupon.config.CouponConfigEditRequest;
import com.eghm.service.business.CouponConfigService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Service("couponConfigService")
@AllArgsConstructor
@Slf4j
public class CouponConfigServiceImpl implements CouponConfigService {

    private final CouponConfigMapper couponConfigMapper;

    @Override
    public void create(CouponConfigAddRequest request) {
        // TODO 优惠券关联商品
        CouponConfig config = DataUtil.copy(request, CouponConfig.class);
        couponConfigMapper.insert(config);
    }

    @Override
    public void update(CouponConfigEditRequest request) {
        // TODO 产品关联关系变更
        CouponConfig config = DataUtil.copy(request, CouponConfig.class);
        couponConfigMapper.updateById(config);
    }
}
