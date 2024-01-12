package com.eghm.mapper;

import com.eghm.model.ItemStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.item.store.ItemStoreVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 店铺信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-01
 */
public interface ItemStoreMapper extends BaseMapper<ItemStore> {

    /**
     * 店铺推荐列表
     * @param limit 限制多少条
     * @return 列表
     */
    List<ItemStoreVO> getRecommend(@Param("limit") int limit);

    /**
     * 更新评分
     *
     * @param id id
     * @param score 评分
     */
    void updateScore(@Param("id") Long id, @Param("score") BigDecimal score);

    /**
     * 店铺列表查询
     * @param storeIds 店铺id
     * @return 列表
     */
    List<ItemStoreVO> getList(@Param("storeIds") List<Long> storeIds);
}
