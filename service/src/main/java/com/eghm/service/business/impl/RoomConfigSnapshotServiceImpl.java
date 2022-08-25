package com.eghm.service.business.impl;

import com.eghm.dao.mapper.RoomConfigSnapshotMapper;
import com.eghm.dao.model.HomestayRoomConfig;
import com.eghm.dao.model.RoomConfigSnapshot;
import com.eghm.service.business.RoomConfigSnapshotService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("roomConfigSnapshotService")
@Slf4j
@AllArgsConstructor
public class RoomConfigSnapshotServiceImpl implements RoomConfigSnapshotService {

    private final RoomConfigSnapshotMapper roomConfigSnapshotMapper;

    @Override
    public void orderSnapshot(String orderNo, List<HomestayRoomConfig> configList) {
        for (HomestayRoomConfig config : configList) {
            RoomConfigSnapshot snapshot = DataUtil.copy(config, RoomConfigSnapshot.class);
            snapshot.setOrderNo(orderNo);
            roomConfigSnapshotMapper.insert(snapshot);
        }
    }
}
