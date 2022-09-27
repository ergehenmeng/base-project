package com.eghm.mapper;

import com.eghm.model.ProductSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品规格表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-01
 */
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    /**
     * 更新库存信息
     * @param skuId 库存id
     * @param num 数量
     * @return 1
     */
    int updateStock(@Param("skuId") Long skuId, @Param("num") Integer num);
}
