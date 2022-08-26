package com.eghm.service.business.impl;

import com.eghm.dao.mapper.LineDayConfigMapper;
import com.eghm.service.business.LineDayConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/26
 */
@Service("lineDayConfigService")
@AllArgsConstructor
@Slf4j
public class LineDayConfigServiceImpl implements LineDayConfigService {

    private final LineDayConfigMapper lineDayConfigMapper;

}
