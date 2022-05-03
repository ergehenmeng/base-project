package com.eghm.service.sys.impl;

import com.eghm.dao.mapper.ExceptionLogMapper;
import com.eghm.dao.model.ExceptionLog;
import com.eghm.service.sys.ExceptionLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/12/6 16:38
 */
@Service("exceptionLogService")
@AllArgsConstructor
public class ExceptionLogServiceImpl implements ExceptionLogService {

    private final ExceptionLogMapper exceptionLogMapper;

    @Override
    public void insertExceptionLog(ExceptionLog log) {
        exceptionLogMapper.insert(log);
    }
}
