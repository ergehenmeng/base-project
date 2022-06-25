package com.eghm.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.utils.DateUtil;
import com.eghm.dao.mapper.LoginLogMapper;
import com.eghm.dao.model.LoginDevice;
import com.eghm.dao.model.LoginLog;
import com.eghm.model.dto.ext.LoginRecord;
import com.eghm.model.vo.user.LoginDeviceVO;
import com.eghm.service.user.LoginDeviceService;
import com.eghm.service.user.LoginLogService;
import com.eghm.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addLoginLog(LoginRecord loginRecord) {
        LoginLog loginLog = DataUtil.copy(loginRecord, LoginLog.class);
        loginLogMapper.insert(loginLog);
        LoginDevice device = DataUtil.copy(loginRecord, LoginDevice.class);
        loginDeviceService.insertOrUpdateSelective(device);
    }

    @Override
    public LoginDeviceVO getLastLogin(Long userId) {
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
    public void deleteLoginLog(Long userId, String serialNumber) {
        LambdaUpdateWrapper<LoginLog> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LoginLog::getUserId, userId);
        wrapper.eq(LoginLog::getSerialNumber, serialNumber);
        loginLogMapper.delete(wrapper);
    }
}
