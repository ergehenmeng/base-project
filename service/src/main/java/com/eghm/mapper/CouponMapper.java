package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.coupon.config.CouponQueryDTO;
import com.eghm.dto.business.coupon.config.CouponQueryRequest;
import com.eghm.model.Coupon;
import com.eghm.vo.business.coupon.CouponBaseResponse;
import com.eghm.vo.business.coupon.CouponResponse;
import com.eghm.vo.business.coupon.CouponVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 优惠券配置表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-13
 */
public interface CouponMapper extends BaseMapper<Coupon> {

    /**
     * 分页查询优惠券列表
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<CouponResponse> listPage(Page<CouponResponse> page, @Param("param") CouponQueryRequest request);

    /**
     * 更新优惠券库存及领取数量
     *
     * @param id  优惠券id
     * @param num 数量 负数-库存 正数+库存
     */
    int updateStock(@Param("id") Long id, @Param("num") int num);

    /**
     * 分页查询可以领取的优惠券列表
     *
     * @param page 分页信息
     * @param dto  查询条件
     * @return 列表
     */
    Page<CouponVO> getByPage(Page<CouponVO> page, @Param("param") CouponQueryDTO dto);

    /**
     * 查询商品下用户可以领取的优惠券列表
     *
     * @param productId  商品id
     * @param storeId 店铺id
     * @return 优惠券列表
     */
    List<CouponVO> getProductCoupon(@Param("productId") Long productId, @Param("storeId") Long storeId);

    /**
     * 查询优惠券详情
     *
     * @param id id
     * @return 优惠券详情
     */
    CouponVO getDetail(@Param("id") Long id);

    /**
     *  获取优惠券列表
     *
     * @param mode 领取方式 1:页面领取 2: 手动发放 3: 订单支付
     * @return 优惠券
     */
    List<CouponBaseResponse> getList(@Param("mode") Integer mode);
}
