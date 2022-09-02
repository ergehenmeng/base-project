package com.eghm.service.business.impl;

import com.eghm.dao.mapper.LineDaySnapshotMapper;
import com.eghm.dao.model.LineDayConfig;
import com.eghm.dao.model.LineDaySnapshot;
import com.eghm.service.business.LineDaySnapshotService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/9/1
 */
@Service("lineDaySnapshotService")
@Slf4j
@AllArgsConstructor
public class LineDaySnapshotServiceImpl implements LineDaySnapshotService {

    private final LineDaySnapshotMapper lineDaySnapshotMapper;

    @Override
    public void insert(Long lineId, String orderNo, List<LineDayConfig> configList) {
        for (LineDayConfig config : configList) {
            LineDaySnapshot snapshot = DataUtil.copy(config, LineDaySnapshot.class);
            snapshot.setLineId(lineId);
            snapshot.setOrderNo(orderNo);
            lineDaySnapshotMapper.insert(snapshot);
        }
    }
}
