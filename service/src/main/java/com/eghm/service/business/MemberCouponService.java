package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.coupon.member.GrantCouponDTO;
import com.eghm.dto.business.coupon.member.MemberCouponQueryPageDTO;
import com.eghm.dto.business.coupon.member.MemberCouponQueryRequest;
import com.eghm.dto.business.coupon.member.ReceiveCouponDTO;
import com.eghm.vo.business.coupon.MemberCouponBaseVO;
import com.eghm.vo.business.coupon.MemberCouponResponse;
import com.eghm.vo.business.coupon.MemberCouponVO;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2022/7/13
 */
public interface MemberCouponService {

    /**
     * 分页查询用户优惠券信息, 包含用户信息
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<MemberCouponResponse> getByPage(MemberCouponQueryRequest request);

    /**
     * 普通用户领取优惠券
     *
     * @param dto 优惠券
     */
    void receiveCoupon(ReceiveCouponDTO dto);

    /**
     * 系统发放优惠券
     *
     * @param dto 优惠券信息
     */
    void grantCoupon(GrantCouponDTO dto);

    /**
     * 用户优惠券列表
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<MemberCouponVO> memberCouponPage(MemberCouponQueryPageDTO dto);

    /**
     * 选择产品可以使用的优惠券
     *
     * @param memberId 用户id
     * @param itemId   产品id
     * @return 优惠券列表
     */
    List<MemberCouponBaseVO> selectCoupon(Long memberId, Long itemId);

    /**
     * 获取优惠券优惠的金额(校验优惠券是否符合使用条件) 零售专用
     *
     * @param memberId  用户id
     * @param couponId  用户优惠券id 不能为空
     * @param itemIds   零售商品id
     * @param storeId   商品所属店铺id
     * @param amount    预支付金额 单位:分
     * @return 优惠金额 单位:分
     */
    Integer getCouponAmountWithVerify(Long memberId, @NonNull Long couponId, List<Long> itemIds, Long storeId, Integer amount);

    /**
     * 使用优惠券(更新优惠券状态为已使用)
     *
     * @param id id
     */
    void useCoupon(Long id);

    /**
     * 使用优惠券(更新优惠券状态为已使用)
     *
     * @param ids ids
     */
    void useCoupon(List<Long> ids);

    /**
     * 释放优惠券 <br>
     * 不在有效期则更新为已过期,否则更新为未使用
     *
     * @param id id
     */
    void releaseCoupon(Long id);

    /**
     * 统计用户领取的优惠券的数量
     *
     * @param memberId  用户id
     * @param couponIds 优惠券
     * @return 每种优惠券的数量 key:优惠券id value:数量(可能为空)
     */
    Map<Long, Integer> countMemberReceived(Long memberId, List<Long> couponIds);

    /**
     * 优惠券过期
     *
     * @param couponId 优惠券id
     */
    void couponExpire(Long couponId);
}
