package com.eghm.service.business.impl;

import com.eghm.dao.mapper.LineConfigMapper;
import com.eghm.service.business.LineConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/26
 */
@Service("lineConfigService")
@AllArgsConstructor
@Slf4j
public class LineConfigServiceImpl implements LineConfigService {

    private final LineConfigMapper lineConfigMapper;

}
