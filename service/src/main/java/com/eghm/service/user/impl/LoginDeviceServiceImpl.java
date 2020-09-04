package com.eghm.service.user.impl;

import com.eghm.dao.mapper.LoginDeviceMapper;
import com.eghm.dao.model.LoginLog;
import com.eghm.service.user.LoginDeviceService;
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

    @Autowired
    public void setLoginDeviceMapper(LoginDeviceMapper loginDeviceMapper) {
        this.loginDeviceMapper = loginDeviceMapper;
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public LoginLog getBySerialNumber(Integer userId, String serialNumber) {
        return loginLogMapper.getBySerialNumber(userId, serialNumber);
    }
}
