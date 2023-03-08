package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.CouponConfig;
import com.eghm.model.dto.business.coupon.config.CouponQueryDTO;
import com.eghm.model.vo.coupon.CouponListVO;
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
public interface CouponConfigMapper extends BaseMapper<CouponConfig> {

    /**
     * 更新优惠券库存及领取数量
     * @param id 优惠券id
     * @param num 数量 负数-库存 正数+库存
     */
    int updateStock(@Param("id") Long id, @Param("num") int num);

    /**
     * 分页查询可以领取的优惠券列表
     * @param page 分页信息
     * @param dto 查询条件
     * @return 列表
     */
    Page<CouponListVO> getByPage(Page<CouponListVO> page, @Param("param") CouponQueryDTO dto);

    /**
     * 查询商品下用户可以领取的优惠券列表
     * @param itemId 商品id
     * @param storeId   店铺id
     * @return 优惠券列表
     */
    List<CouponListVO> getItemCoupon(@Param("itemId") Long itemId, @Param("storeId") Long storeId);
}
