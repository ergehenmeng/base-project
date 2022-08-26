package com.eghm.service.business.impl;

import com.eghm.dao.mapper.LineMapper;
import com.eghm.service.business.LineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/8/26
 */
@Service("lineService")
@AllArgsConstructor
@Slf4j
public class LineServiceImpl implements LineService {

    private final LineMapper lineMapper;

    
}
