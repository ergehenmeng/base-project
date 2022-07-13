package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.CouponConfig;
import com.eghm.model.dto.business.coupon.config.CouponConfigAddRequest;
import com.eghm.model.dto.business.coupon.config.CouponConfigEditRequest;
import com.eghm.model.dto.business.coupon.config.CouponConfigQueryRequest;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
public interface CouponConfigService {

    /**
     * 分页查询优惠券配置列表
     * @param request 查询条件
     * @return 列表
     */
    Page<CouponConfig> getByPage(CouponConfigQueryRequest request);

    /**
     * 创建优惠券配置信息
     * @param request 优惠券配置信息
     */
    void create(CouponConfigAddRequest request);

    /**
     * 更新优惠券配置信息
     * @param request 优惠券配置
     */
    void update(CouponConfigEditRequest request);

    /**
     * 更新优惠券配置状态
     * @param id 主键
     * @param state 新状态 0:禁用 1:启用
     */
    void updateState(Long id, Integer state);

    /**
     * 主键查询
     * @param id id
     * @return 优惠券
     */
    CouponConfig selectById(Long id);
}
