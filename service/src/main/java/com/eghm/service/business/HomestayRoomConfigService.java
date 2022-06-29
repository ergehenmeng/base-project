package com.eghm.service.business;

import com.eghm.model.dto.homestay.room.config.RoomConfigQueryRequest;
import com.eghm.model.dto.homestay.room.config.RoomConfigRequest;
import com.eghm.model.vo.homestay.room.RoomConfigResponse;

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
}
