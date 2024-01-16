package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.PoiLinePoint;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 线路点位关联表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-22
 */
public interface PoiLinePointMapper extends BaseMapper<PoiLinePoint> {

    /**
     * 获取线路点位id
     *
     * @param lineId 线路
     * @return 点位id
     */
    List<Long> getList(@Param("lineId") Long lineId);
}
