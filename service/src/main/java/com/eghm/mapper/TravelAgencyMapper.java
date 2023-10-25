package com.eghm.mapper;

import com.eghm.model.TravelAgency;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 旅行社信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-02-18
 */
public interface TravelAgencyMapper extends BaseMapper<TravelAgency> {

    /**
     * 更新评分
     *
     * @param id id
     * @param score 评分
     */
    void updateScore(@Param("id") Long id, @Param("score") BigDecimal score);
}
