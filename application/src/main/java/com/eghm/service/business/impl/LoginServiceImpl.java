package com.eghm.service.business.impl;

import com.eghm.dto.ext.Page;
import com.eghm.common.IdGenerator;
import com.eghm.business.model.LoginDevice;
import com.eghm.business.model.LoginLog;
import com.eghm.business.repository.LoginRepository;
import com.eghm.dto.business.member.LoginLogQueryRequest;
import com.eghm.dto.ext.LoginRecord;
import com.eghm.service.business.LoginQueryGateway;
import com.eghm.service.business.LoginService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DateUtil;
import com.eghm.vo.business.member.LoginDeviceVO;
import com.eghm.vo.business.member.LoginLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/3/23
 */
@AllArgsConstructor
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;

    private final LoginQueryGateway loginQueryGateway;

    private final IdGenerator idGenerator;

    @Override
    public Page<LoginLogResponse> getByPage(LoginLogQueryRequest request) {
        return loginQueryGateway.listLoginLog(request);
    }

    @Override
    public void insertLoginLog(LoginRecord loginRecord) {
        LoginLog loginLog = DataUtil.copy(loginRecord, LoginLog.class);
        loginRepository.saveLoginLog(loginLog);
        LoginDevice device = DataUtil.copy(loginRecord, LoginDevice.class);
        device.setId(idGenerator.nextId());
        loginRepository.saveOrUpdateDevice(device);
    }

    @Override
    public void deleteLoginDevice(Long memberId, Long id) {
        loginRepository.deleteLoginDevice(memberId, id);
    }

    @Override
    public LoginDevice getBySerialNumber(Long memberId, String serialNumber) {
        return loginRepository.findDeviceBySerialNumber(memberId, serialNumber);
    }

    @Override
    public List<LoginDeviceVO> getByMemberId(Long memberId) {
        List<LoginDevice> deviceList = loginQueryGateway.listDeviceByMemberId(memberId);
        return DataUtil.copy(deviceList, device -> {
            LoginDeviceVO vo = DataUtil.copy(device, LoginDeviceVO.class, "loginTime");
            vo.setLoginTime(DateUtil.formatSimple(device.getLoginTime()));
            return vo;
        });
    }
}
