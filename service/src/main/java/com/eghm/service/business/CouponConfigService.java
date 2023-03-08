package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.CouponConfig;
import com.eghm.model.dto.business.coupon.config.CouponConfigAddRequest;
import com.eghm.model.dto.business.coupon.config.CouponConfigEditRequest;
import com.eghm.model.dto.business.coupon.config.CouponConfigQueryRequest;
import com.eghm.model.dto.business.coupon.config.CouponQueryDTO;
import com.eghm.model.vo.coupon.CouponListVO;

import java.util.List;

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

    /**
     * 分页查询可以领取的优惠券列表  注意如果当前用户已的登陆, 则会根据用户是否领取过该优惠券来返回是否能领取状态
     * @param dto 查询条件
     * @return 列表
     */
    List<CouponListVO> getByPage(CouponQueryDTO dto);

    /**
     * 查询商品下用户可以领取的优惠券列表. 注意如果当前用户已的登陆, 则会根据用户是否领取过该优惠券来返回是否能领取状态
     * @param itemId 商品id
     * @return 优惠券列表
     */
    List<CouponListVO> getItemCoupon(Long itemId);
}
