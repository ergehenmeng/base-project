package com.eghm.service.business.impl;

import com.eghm.mapper.LineOrderSnapshotMapper;
import com.eghm.model.LineDayConfig;
import com.eghm.model.LineOrderSnapshot;
import com.eghm.service.business.LineOrderSnapshotService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/9/1
 */
@Service("lineOrderSnapshotService")
@Slf4j
@AllArgsConstructor
public class LineOrderSnapshotServiceImpl implements LineOrderSnapshotService {

    private final LineOrderSnapshotMapper lineOrderSnapshotMapper;

    @Override
    public void insert(Long lineId, String orderNo, List<LineDayConfig> configList) {
        for (LineDayConfig config : configList) {
            LineOrderSnapshot snapshot = DataUtil.copy(config, LineOrderSnapshot.class);
            snapshot.setLineId(lineId);
            snapshot.setOrderNo(orderNo);
            lineOrderSnapshotMapper.insert(snapshot);
        }
    }
}
