package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.MemberCoupon;
import com.eghm.dto.business.coupon.member.MemberCouponQueryPageDTO;
import com.eghm.dto.business.coupon.member.MemberCouponQueryRequest;
import com.eghm.vo.coupon.MemberCouponBaseVO;
import com.eghm.vo.coupon.MemberCouponCountVO;
import com.eghm.vo.coupon.MemberCouponResponse;
import com.eghm.vo.coupon.MemberCouponVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户优惠券表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-13
 */
public interface MemberCouponMapper extends BaseMapper<MemberCoupon> {

    /**
     * 查询用户优惠券信息,含用户信息
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<MemberCouponResponse> getByPage(Page<MemberCouponVO> page, @Param("param") MemberCouponQueryRequest request);

    /**
     * 用户优惠券列表查询
     * @param page 分页信息
     * @param dto 查询条件
     * @return 列表
     */
    Page<MemberCouponVO> memberCouponPage(Page<MemberCouponVO> page, @Param("param") MemberCouponQueryPageDTO dto);

    /**
     * 选择某个产品可以使用的优惠券列表
     * @param memberId 用户id
     * @param productId 产品id
     * @return 优惠券列表
     */
    List<MemberCouponBaseVO> selectCoupon(@Param("memberId") Long memberId, @Param("productId") Long productId);

    /**
     * 统计用户领取的优惠券的数量
     * @param memberId 用户id
     * @param couponIds 优惠券id
     * @return 每种优惠券的数量 key:优惠券id value:数量(可能为空)
     */
    List<MemberCouponCountVO> countMemberReceived(@Param("memberId") Long memberId, @Param("couponIds") List<Long> couponIds);
}
