package com.eghm.service.business;

import com.eghm.dao.model.HomestayRoomConfig;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
public interface RoomConfigSnapshotService {

    /**
     * 保存订单快照
     * @param orderNo 订单信息
     * @param configList 房型价格信息
     */
    void orderSnapshot(String orderNo, List<HomestayRoomConfig> configList);
}
