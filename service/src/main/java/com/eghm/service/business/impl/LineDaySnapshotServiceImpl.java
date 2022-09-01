package com.eghm.service.business.impl;

import com.eghm.dao.mapper.LineDaySnapshotMapper;
import com.eghm.service.business.LineDaySnapshotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/9/1
 */
@Service("lineDaySnapshotService")
@Slf4j
@AllArgsConstructor
public class LineDaySnapshotServiceImpl implements LineDaySnapshotService {

    private final LineDaySnapshotMapper lineDaySnapshotMapper;

}
