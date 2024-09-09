package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.VenueSitePrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 场馆场次价格信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
public interface VenueSitePriceMapper extends BaseMapper<VenueSitePrice> {

    /**
     * 更新库存
     *
     * @param ids  id
     * @param num 数量 负数-库存 正数+库存
     * @return 1
     */
    int updateStock(@Param("ids") List<Long> ids, @Param("num") int num);
}
