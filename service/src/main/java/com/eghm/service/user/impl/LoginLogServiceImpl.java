package com.eghm.service.user.impl;

import com.eghm.common.utils.DateUtil;
import com.eghm.dao.mapper.LoginLogMapper;
import com.eghm.dao.model.LoginDevice;
import com.eghm.dao.model.LoginLog;
import com.eghm.model.ext.LoginRecord;
import com.eghm.model.vo.user.LoginDeviceVO;
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
    public LoginDeviceVO getLastLogin(Integer userId) {
        LoginLog lastLogin = loginLogMapper.getLastLogin(userId);
        if (lastLogin == null) {
            return null;
        }
        LoginDeviceVO vo = new LoginDeviceVO();
        vo.setLoginTime(DateUtil.formatSimple(lastLogin.getAddTime()));
        vo.setDeviceModel(lastLogin.getDeviceModel());
        return vo;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteLoginLog(Integer userId, String serialNumber) {
        loginLogMapper.deleteLoginLog(userId, serialNumber);
    }
}
