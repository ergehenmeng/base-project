package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.ItemSku;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品sku表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-06
 */
public interface ItemSkuMapper extends BaseMapper<ItemSku> {

    /**
     * 更新库存信息
     *
     * @param skuId 库存id
     * @param num   数量
     * @return 1
     */
    int updateStock(@Param("skuId") Long skuId, @Param("num") Integer num);
}
