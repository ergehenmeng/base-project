package com.eghm.dao.mapper;

import com.eghm.dao.model.HomestayRoomConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

/**
 * <p>
 * 房间价格配置表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-25
 */
public interface HomestayRoomConfigMapper extends BaseMapper<HomestayRoomConfig> {

    /**
     * 新增或更新房态信息
     * @param config 房态信息
     * @return 条数
     */
    int insertOrUpdate(HomestayRoomConfig config);

    /**
     * 更新指定时间段内房态的库存信息
     * @param roomId 房型id
     * @param startDate 开始时间 包含
     * @param endDate 截止时间 包含
     * @param num 正数+库存,负数-库存
     */
    int updateStock(@Param("roomId") Long roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("num") Integer num);
}
