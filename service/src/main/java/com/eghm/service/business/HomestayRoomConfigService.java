package com.eghm.service.business;

import com.eghm.model.HomestayRoomConfig;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigEditRequest;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigQueryRequest;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigRequest;
import com.eghm.model.vo.business.homestay.room.config.RoomConfigResponse;
import com.eghm.model.vo.business.homestay.room.config.RoomConfigVO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/25
 */
public interface HomestayRoomConfigService {

    /**
     * 设置房型信息
     * @param request 设置信息
     */
    void setup(RoomConfigRequest request);

    /**
     * 按月查询房型设置信息
     * @param request 月份+房型id
     * @return 房型信息
     */
    List<RoomConfigResponse> getList(RoomConfigQueryRequest request);

    /**
     * 更新某一天的房态信息
     * @param request 新房态信息
     */
    void update(RoomConfigEditRequest request);

    /**
     * 查询某个房型当月的价格信息
     * @param month  月份
     * @param roomId 房型id
     * @return 价格列表
     */
    List<RoomConfigVO> getList(LocalDate month, Long roomId);

    /**
     * 获取房间某一天的价格配置信息
     * @param roomId 房间id
     * @param configDate 日期
     * @return 价格库存配置信息
     */
    HomestayRoomConfig getConfig(Long roomId, LocalDate configDate);

    /**
     * 查询指定房型的房态信息
     * @param roomId 房型id
     * @param startDate 开始时间(含)
     * @param endDate 结束时间(含)
     * @return 房态列表
     */
    List<HomestayRoomConfig> getList(Long roomId, LocalDate startDate, LocalDate endDate);

    /**
     * 更新库存信息
     * @param roomId 房型id
     * @param startDate 开始时间 包含
     * @param endDate 截止时间 包含
     * @param num 正数+库存,负数-库存
     */
    void updateStock(Long roomId, LocalDate startDate, LocalDate endDate, Integer num);
}
