package com.eghm.service.member.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.LoginRecord;
import com.eghm.mapper.LoginDeviceMapper;
import com.eghm.mapper.LoginLogMapper;
import com.eghm.model.LoginDevice;
import com.eghm.model.LoginLog;
import com.eghm.service.member.LoginService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DateUtil;
import com.eghm.vo.member.LoginDeviceVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/3/23
 */
@Service("loginService")
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginDeviceMapper loginDeviceMapper;

    private final LoginLogMapper loginLogMapper;

    @Override
    public void insertLoginLog(LoginRecord loginRecord) {
        LoginLog loginLog = DataUtil.copy(loginRecord, LoginLog.class);
        loginLogMapper.insert(loginLog);
        LoginDevice device = DataUtil.copy(loginRecord, LoginDevice.class);
        loginDeviceMapper.insertOrUpdateSelective(device);
    }

    @Override
    public LoginDeviceVO getLastLogin(Long memberId) {
        LoginLog lastLogin = loginLogMapper.getLastLogin(memberId);
        if (lastLogin == null) {
            return null;
        }
        LoginDeviceVO vo = new LoginDeviceVO();
        vo.setLoginTime(DateUtil.formatSimple(lastLogin.getCreateTime()));
        vo.setDeviceModel(lastLogin.getDeviceModel());
        return vo;
    }

    @Override
    public void deleteLoginLog(Long memberId, String serialNumber) {
        LambdaUpdateWrapper<LoginLog> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LoginLog::getMemberId, memberId);
        wrapper.eq(LoginLog::getSerialNumber, serialNumber);
        loginLogMapper.delete(wrapper);
    }

    @Override
    public void insertOrUpdateSelective(LoginDevice device) {
        loginDeviceMapper.insertOrUpdateSelective(device);
    }

    @Override
    public void deleteLoginDevice(Long memberId, String serialNumber) {
        loginDeviceMapper.deleteLoginDevice(memberId, serialNumber);
        LambdaUpdateWrapper<LoginLog> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(LoginLog::getMemberId, memberId);
        wrapper.eq(LoginLog::getSerialNumber, serialNumber);
        loginLogMapper.delete(wrapper);
    }

    @Override
    public LoginDevice getBySerialNumber(Long memberId, String serialNumber) {
        return loginDeviceMapper.getBySerialNumber(memberId, serialNumber);
    }

    @Override
    public List<LoginDeviceVO> getByMemberId(Long memberId) {
        LambdaQueryWrapper<LoginDevice> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LoginDevice::getMemberId, memberId);
        wrapper.orderByDesc(LoginDevice::getId);
        List<LoginDevice> deviceList = loginDeviceMapper.selectList(wrapper);

        return DataUtil.copy(deviceList, device -> {
            LoginDeviceVO vo = DataUtil.copy(device, LoginDeviceVO.class, "loginTime");
            vo.setLoginTime(DateUtil.formatSimple(device.getLoginTime()));
            return vo;
        });
    }
}
