package com.eghm.service.sys.impl;

import com.eghm.dao.mapper.ExceptionLogMapper;
import com.eghm.dao.model.ExceptionLog;
import com.eghm.service.common.KeyGenerator;
import com.eghm.service.sys.ExceptionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 二哥很猛
 * @date 2019/12/6 16:38
 */
@Service("exceptionLogService")
public class ExceptionLogServiceImpl implements ExceptionLogService {

    private ExceptionLogMapper exceptionLogMapper;

    private KeyGenerator keyGenerator;

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Autowired
    public void setExceptionLogMapper(ExceptionLogMapper exceptionLogMapper) {
        this.exceptionLogMapper = exceptionLogMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertExceptionLog(ExceptionLog log) {
        log.setId(keyGenerator.generateKey());
        exceptionLogMapper.insertSelective(log);
    }
}
