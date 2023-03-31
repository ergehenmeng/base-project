package com.eghm.mapper;

import com.eghm.model.HomestayRoomConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.homestay.room.config.HomestayMinPriceVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

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
     * @return 1
     */
    int updateStock(@Param("roomId") Long roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("num") Integer num);

    /**
     * 查询民宿下所有房型中 在一段时间内的最低价
     * @param homestayList 民宿id列表
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 民宿最低价
     */
    List<HomestayMinPriceVO> getHomestayMinPrice(@Param("homestayList") List<Long> homestayList, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 查询房型 在一段时间内的最低价(不含售罄和未销售的房型)
     * @param roomId    房型id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 房型最低价 可能为空
     */
    Integer getRoomMinPrice(@Param("roomId") Long roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
