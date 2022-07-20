package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.UserCoupon;
import com.eghm.model.dto.business.coupon.user.UserCouponQueryPageDTO;
import com.eghm.model.dto.business.coupon.user.UserCouponQueryRequest;
import com.eghm.model.vo.coupon.UserCouponBaseVO;
import com.eghm.model.vo.coupon.UserCouponResponse;
import com.eghm.model.vo.coupon.UserCouponVO;
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
public interface UserCouponMapper extends BaseMapper<UserCoupon> {

    /**
     * 查询用户优惠券信息,含用户信息
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<UserCouponResponse> getByPage(Page<UserCouponVO> page, UserCouponQueryRequest request);

    /**
     * 用户优惠券列表查询
     * @param page 分页信息
     * @param dto 查询条件
     * @return 列表
     */
    Page<UserCouponVO> userCouponPage(Page<UserCouponVO> page, UserCouponQueryPageDTO dto);

    /**
     * 选择某个产品可以使用的优惠券列表
     * @param userId 用户id
     * @param productId 产品id
     * @return 优惠券列表
     */
    List<UserCouponBaseVO> selectCoupon(@Param("userId") Long userId, @Param("productId") Long productId);

}
