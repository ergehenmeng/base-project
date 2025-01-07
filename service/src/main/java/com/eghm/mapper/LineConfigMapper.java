package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.LineConfig;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

/**
 * <p>
 * 线路商品配置表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-26
 */
public interface LineConfigMapper extends BaseMapper<LineConfig> {

    /**
     * 新增或更新线路日态信息
     *
     * @param config 线路日态
     */
    void insertUpdate(LineConfig config);

    /**
     * 更新库存信息
     *
     * @param id  id
     * @param num 正数+库存 负数-库存
     * @return 1
     */
    int updateStock(@Param("id") Long id, @Param("num") Integer num);

    /**
     * 从某一段时间开始查询最低的线路价格
     *
     * @param lineId    线路id
     * @param startDate 开始日期
     * @return 最低价 单位:分
     */
    Integer getMinPrice(@Param("lineId") Long lineId, @Param("startDate") LocalDate startDate);
}
