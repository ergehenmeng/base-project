package com.eghm.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.utils.DateUtil;
import com.eghm.dao.mapper.LoginDeviceMapper;
import com.eghm.dao.model.LoginDevice;
import com.eghm.model.vo.user.LoginDeviceVO;
import com.eghm.service.user.LoginDeviceService;
import com.eghm.service.user.LoginLogService;
import com.eghm.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void insertOrUpdateSelective(LoginDevice device) {
        loginDeviceMapper.insertOrUpdateSelective(device);
    }

    @Override
    public void deleteLoginDevice(Long userId, String serialNumber) {
        loginDeviceMapper.deleteLoginDevice(userId, serialNumber);
        loginLogService.deleteLoginLog(userId, serialNumber);
    }

    @Override
    public LoginDevice getBySerialNumber(Long userId, String serialNumber) {
        return loginDeviceMapper.getBySerialNumber(userId, serialNumber);
    }

    @Override
    public List<LoginDeviceVO> getByUserId(Long userId) {
        LambdaQueryWrapper<LoginDevice> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LoginDevice::getUserId, userId);
        wrapper.last(" order by id desc ");
        List<LoginDevice> deviceList = loginDeviceMapper.selectList(wrapper);

        return DataUtil.convert(deviceList, device -> {
            LoginDeviceVO vo = DataUtil.copy(device, LoginDeviceVO.class, "loginTime");
            vo.setLoginTime(DateUtil.formatSimple(device.getLoginTime()));
            return vo;
        });
    }
}
