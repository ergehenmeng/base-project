package com.eghm.service.business.impl;

import com.eghm.dao.mapper.HomestayOrderSnapshotMapper;
import com.eghm.dao.model.HomestayRoomConfig;
import com.eghm.dao.model.HomestayOrderSnapshot;
import com.eghm.service.business.HomestayOrderSnapshotService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/25
 */
@Service("homestayOrderSnapshotService")
@Slf4j
@AllArgsConstructor
public class HomestayOrderSnapshotServiceImpl implements HomestayOrderSnapshotService {

    private final HomestayOrderSnapshotMapper homestayOrderSnapshotMapper;

    @Override
    public void orderSnapshot(String orderNo, List<HomestayRoomConfig> configList) {
        for (HomestayRoomConfig config : configList) {
            HomestayOrderSnapshot snapshot = DataUtil.copy(config, HomestayOrderSnapshot.class);
            snapshot.setOrderNo(orderNo);
            homestayOrderSnapshotMapper.insert(snapshot);
        }
    }
}
