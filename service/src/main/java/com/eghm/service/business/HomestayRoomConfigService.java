package com.eghm.service.business;

import com.eghm.model.dto.homestay.room.config.RoomConfigRequest;

/**
 * @author 二哥很猛 2022/6/25 14:11
 */
public interface HomestayRoomConfigService {

    /**
     * 设置房型信息
     * @param request 设置信息
     */
    void setup(RoomConfigRequest request);
}
