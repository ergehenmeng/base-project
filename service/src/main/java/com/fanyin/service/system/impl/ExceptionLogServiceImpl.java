package com.fanyin.service.system.impl;

import com.fanyin.dao.mapper.system.ExceptionLogMapper;
import com.fanyin.dao.model.system.ExceptionLog;
import com.fanyin.service.system.ExceptionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/12/6 16:38
 */
@Service("exceptionLogService")
public class ExceptionLogServiceImpl implements ExceptionLogService {

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    @Override
    public void insertExceptionLog(ExceptionLog log) {
        exceptionLogMapper.insertSelective(log);
    }
}
