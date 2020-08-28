package com.eghm.service.sys.impl;

import com.eghm.dao.mapper.system.ExceptionLogMapper;
import com.eghm.dao.model.system.ExceptionLog;
import com.eghm.service.sys.ExceptionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/12/6 16:38
 */
@Service("exceptionLogService")
public class ExceptionLogServiceImpl implements ExceptionLogService {

    private ExceptionLogMapper exceptionLogMapper;

    @Autowired
    public void setExceptionLogMapper(ExceptionLogMapper exceptionLogMapper) {
        this.exceptionLogMapper = exceptionLogMapper;
    }

    @Override
    public void insertExceptionLog(ExceptionLog log) {
        exceptionLogMapper.insertSelective(log);
    }
}
