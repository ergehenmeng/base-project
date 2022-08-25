package com.eghm.dao.mapper;

import com.eghm.dao.model.RestaurantVoucher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 餐饮代金券 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-30
 */
public interface RestaurantVoucherMapper extends BaseMapper<RestaurantVoucher> {

    /**
     * 更新库存
     * @param id id
     * @param num 数量 负数-库存 正数+库存
     * @return 1
     */
    int updateStock(@Param("id") Long id, @Param("num")int num);
}
