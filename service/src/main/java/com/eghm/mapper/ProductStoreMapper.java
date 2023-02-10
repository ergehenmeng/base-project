package com.eghm.mapper;

import com.eghm.model.ProductStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.vo.business.product.store.ProductStoreVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 店铺信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-01
 */
public interface ProductStoreMapper extends BaseMapper<ProductStore> {

    /**
     * 店铺推荐列表
     * @param limit 限制多少条
     * @return 列表
     */
    List<ProductStoreVO> getRecommend(@Param("limit") int limit);
}
