package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.coupon.config.CouponAddRequest;
import com.eghm.dto.business.coupon.config.CouponEditRequest;
import com.eghm.dto.business.coupon.config.CouponQueryDTO;
import com.eghm.dto.business.coupon.config.CouponQueryRequest;
import com.eghm.model.Coupon;
import com.eghm.vo.business.coupon.CouponVO;
import com.eghm.vo.business.coupon.CouponResponse;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/13
 */
public interface CouponService {

    /**
     * 分页查询优惠券配置列表
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<CouponResponse> getByPage(CouponQueryRequest request);

    /**
     * 创建优惠券配置信息
     *
     * @param request 优惠券配置信息
     */
    void create(CouponAddRequest request);

    /**
     * 更新优惠券配置信息
     *
     * @param request 优惠券配置
     */
    void update(CouponEditRequest request);

    /**
     * 更新优惠券配置状态
     *
     * @param id    主键
     * @param state 新状态 0:禁用 1:启用
     */
    void updateState(Long id, Integer state);

    /**
     * 主键查询
     *
     * @param id id
     * @return 优惠券
     */
    Coupon selectById(Long id);

    /**
     * 主键查询
     *
     * @param id id
     * @return 优惠券
     */
    Coupon selectByIdRequired(Long id);

    /**
     * 分页查询可以领取的优惠券列表  注意如果当前用户已的登陆, 则会根据用户是否领取过该优惠券来返回是否能领取状态
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<CouponVO> getByPage(CouponQueryDTO dto);

    /**
     * 查询商品下用户可以领取的优惠券列表. 注意如果当前用户已的登陆, 则会根据用户是否领取过该优惠券来返回是否能领取状态
     *
     * @param itemId 商品id
     * @return 优惠券列表
     */
    List<CouponVO> getItemCoupon(Long itemId);

    /**
     * 查询优惠券详情
     *
     * @param id id
     * @return 优惠券信息
     */
    CouponVO getDetail(Long id);
}
