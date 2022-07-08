package com.eghm.service.business;

import com.eghm.model.dto.business.homestay.room.config.RoomConfigEditRequest;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigQueryRequest;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigRequest;
import com.eghm.model.vo.business.homestay.room.config.RoomConfigResponse;
import com.eghm.model.vo.business.homestay.room.config.RoomConfigVO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/25 14:11
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
}
