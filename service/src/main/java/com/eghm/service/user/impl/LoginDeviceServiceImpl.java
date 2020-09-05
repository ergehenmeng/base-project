package com.eghm.service.user.impl;

import com.eghm.dao.mapper.LoginDeviceMapper;
import com.eghm.dao.model.LoginDevice;
import com.eghm.dao.model.LoginLog;
import com.eghm.service.user.LoginDeviceService;
import com.eghm.service.user.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
@Service("loginDeviceService")
public class LoginDeviceServiceImpl implements LoginDeviceService {

    private LoginDeviceMapper loginDeviceMapper;

    private LoginLogService loginLogService;

    @Autowired
    public void setLoginDeviceMapper(LoginDeviceMapper loginDeviceMapper) {
        this.loginDeviceMapper = loginDeviceMapper;
    }

    @Autowired
    public void setLoginLogService(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertOrUpdateSelective(LoginDevice device) {
        loginDeviceMapper.insertOrUpdateSelective(device);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteLoginDevice(Integer userId, String serialNumber) {
        loginDeviceMapper.deleteLoginDevice(userId, serialNumber);
        loginLogService.deleteLoginLog(userId, serialNumber);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public LoginDevice getBySerialNumber(Integer userId, String serialNumber) {
        return loginDeviceMapper.getBySerialNumber(userId, serialNumber);
    }
}
