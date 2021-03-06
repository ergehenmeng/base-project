package com.eghm.service.user.impl;

import com.eghm.common.utils.DateUtil;
import com.eghm.dao.mapper.LoginDeviceMapper;
import com.eghm.dao.model.LoginDevice;
import com.eghm.model.vo.user.LoginDeviceVO;
import com.eghm.service.user.LoginDeviceService;
import com.eghm.service.user.LoginLogService;
import com.eghm.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void deleteLoginDevice(Long userId, String serialNumber) {
        loginDeviceMapper.deleteLoginDevice(userId, serialNumber);
        loginLogService.deleteLoginLog(userId, serialNumber);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public LoginDevice getBySerialNumber(Long userId, String serialNumber) {
        return loginDeviceMapper.getBySerialNumber(userId, serialNumber);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public List<LoginDeviceVO> getByUserId(Long userId) {
        List<LoginDevice> deviceList = loginDeviceMapper.getByUserId(userId);
        return DataUtil.convert(deviceList, device -> {
            LoginDeviceVO vo = DataUtil.copy(device, LoginDeviceVO.class, "loginTime");
            vo.setLoginTime(DateUtil.formatSimple(device.getLoginTime()));
            return vo;
        });
    }
}
