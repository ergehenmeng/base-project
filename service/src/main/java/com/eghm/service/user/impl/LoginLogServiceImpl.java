package com.eghm.service.user.impl;

import com.eghm.dao.mapper.LoginLogMapper;
import com.eghm.dao.model.LoginDevice;
import com.eghm.dao.model.LoginLog;
import com.eghm.model.ext.LoginRecord;
import com.eghm.service.user.LoginDeviceService;
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

    private LoginDeviceService loginDeviceService;

    @Autowired
    public void setLoginDeviceService(LoginDeviceService loginDeviceService) {
        this.loginDeviceService = loginDeviceService;
    }

    @Autowired
    public void setLoginLogMapper(LoginLogMapper loginLogMapper) {
        this.loginLogMapper = loginLogMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addLoginLog(LoginRecord record) {
        LoginLog loginLog = DataUtil.copy(record, LoginLog.class);
        loginLogMapper.insertSelective(loginLog);
        LoginDevice device = DataUtil.copy(record, LoginDevice.class);
        loginDeviceService.insertOrUpdateSelective(device);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public LoginLog getLastLogin(Integer userId) {
        return loginLogMapper.getLastLogin(userId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteLoginLog(Integer userId, String serialNumber) {
        loginLogMapper.deleteLoginLog(userId, serialNumber);
    }
}
