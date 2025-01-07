package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.HomestayRoomConfig;
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
     *
     * @param config 房态信息
     */
    void insertUpdate(HomestayRoomConfig config);

    /**
     * 更新指定时间段内房态的库存信息
     *
     * @param roomId    房型id
     * @param startDate 开始时间 包含
     * @param endDate   截止时间 不含
     * @param num       正数+库存,负数-库存
     * @return 1
     */
    int updateStock(@Param("roomId") Long roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("num") Integer num);

    /**
     * 查询房型 在一段时间内的最低价(不含售罄和未销售的房型)
     *
     * @param roomId    房型id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 房型最低价 可能为空
     */
    Integer getRoomMinPrice(@Param("roomId") Long roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
