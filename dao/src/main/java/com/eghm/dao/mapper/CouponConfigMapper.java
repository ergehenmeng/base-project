package com.eghm.dao.mapper;

import com.eghm.dao.model.CouponConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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
}
