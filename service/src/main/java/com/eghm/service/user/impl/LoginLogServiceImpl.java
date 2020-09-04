package com.eghm.service.user.impl;

import com.eghm.dao.mapper.LoginLogMapper;
import com.eghm.dao.model.LoginLog;
import com.eghm.model.ext.LoginRecord;
import com.eghm.service.user.LoginLogService;
import com.eghm.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 二哥很猛
 * @date 2019/8/28 15:25
 */
@Service("loginLogService")
@Transactional(rollbackFor = RuntimeException.class)
public class LoginLogServiceImpl implements LoginLogService {

    private LoginLogMapper loginLogMapper;

    @Autowired
    public void setLoginLogMapper(LoginLogMapper loginLogMapper) {
        this.loginLogMapper = loginLogMapper;
    }

    @Override
    public void addLoginLog(LoginRecord record) {
        LoginLog loginLog = DataUtil.copy(record, LoginLog.class);
        loginLogMapper.insertSelective(loginLog);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public LoginLog getLastLogin(Integer userId) {
        return loginLogMapper.getLastLogin(userId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public LoginLog getBySerialNumber(Integer userId, String serialNumber) {
        return loginLogMapper.getBySerialNumber(userId, serialNumber);
    }
}
