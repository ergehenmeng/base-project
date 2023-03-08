package com.eghm.mapper;

import com.eghm.model.ItemOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-09-05
 */
public interface ItemOrderMapper extends BaseMapper<ItemOrder> {

    /**
     * 查询订单下所有商品的总数量
     * @param orderNo 订单编号
     * @return 总数量
     */
    int getProductNum(@Param("orderNo") String orderNo);
}
