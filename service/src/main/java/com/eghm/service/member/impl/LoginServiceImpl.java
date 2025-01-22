package com.eghm.service.member.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.LoginRecord;
import com.eghm.dto.member.log.LoginLogQueryRequest;
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

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 二哥很猛
 * @since 2023/3/23
 */
@Service("loginService")
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginLogMapper loginLogMapper;

    private final LoginDeviceMapper loginDeviceMapper;

    @Override
    public Page<LoginLog> getByPage(LoginLogQueryRequest request) {
        LambdaQueryWrapper<LoginLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LoginLog::getMemberId, request.getMemberId());
        wrapper.eq(isNotBlank(request.getChannel()), LoginLog::getChannel, request.getChannel());
        wrapper.ge(request.getStartDate() != null, LoginLog::getCreateTime, request.getStartDate());
        wrapper.le(request.getEndDate() != null, LoginLog::getCreateTime, request.getEndDate());
        wrapper.orderByDesc(LoginLog::getId);
        return loginLogMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void insertLoginLog(LoginRecord loginRecord) {
        LoginLog loginLog = DataUtil.copy(loginRecord, LoginLog.class);
        loginLogMapper.insert(loginLog);
        LoginDevice device = DataUtil.copy(loginRecord, LoginDevice.class);
        device.setId(IdWorker.getId());
        loginDeviceMapper.insertOrUpdateSelective(device);
    }

    @Override
    public void deleteLoginDevice(Long memberId, Long id) {
        loginDeviceMapper.deleteLoginDevice(memberId, id);
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
