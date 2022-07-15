package com.eghm.service.business;

import com.eghm.model.dto.business.coupon.user.ReceiveCouponDTO;
import com.eghm.model.dto.business.coupon.user.UserCouponQueryPageDTO;
import com.eghm.model.vo.coupon.UserCouponBaseVO;
import com.eghm.model.vo.coupon.UserCouponVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
public interface UserCouponService {

    /**
     * 普通用户领取优惠券
     * @param dto 优惠券新
     */
    void receiveCoupon(ReceiveCouponDTO dto);

    /**
     * 统计某个优惠券用户领了多少个
     * @param couponId 优惠券id
     * @param userId 用户id
     * @return 个数
     */
    int receiveCount(Long couponId, Long userId);

    /**
     * 用户优惠券列表
     * @param dto 查询条件
     * @return 列表
     */
    List<UserCouponVO> userCouponPage(UserCouponQueryPageDTO dto);

    /**
     * 选择产品可以使用的优惠券
     * @param userId 用户id
     * @param productId 产品id
     * @param amount 商品金额
     * @return 优惠券列表
     */
    List<UserCouponBaseVO> selectCoupon(Long userId, Long productId, Integer amount);

}
