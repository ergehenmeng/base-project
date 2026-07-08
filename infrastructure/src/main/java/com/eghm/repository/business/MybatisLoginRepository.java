package com.eghm.repository.business;

import com.eghm.business.repository.LoginRepository;
import com.eghm.mapper.LoginDeviceMapper;
import com.eghm.mapper.LoginLogMapper;
import com.eghm.business.model.LoginDevice;
import com.eghm.business.model.LoginLog;
import com.eghm.po.LoginDevicePO;
import com.eghm.po.LoginLogPO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis adapter for login persistence.
 */
@Repository
@AllArgsConstructor
public class MybatisLoginRepository implements LoginRepository {

    private final LoginLogMapper loginLogMapper;

    private final LoginDeviceMapper loginDeviceMapper;

    @Override
    public void saveLoginLog(LoginLog loginLog) {
        loginLogMapper.insert(DataUtil.copy(loginLog, LoginLogPO.class));
    }

    @Override
    public void saveOrUpdateDevice(LoginDevice device) {
        loginDeviceMapper.insertOrUpdateSelective(DataUtil.copy(device, LoginDevicePO.class));
    }

    @Override
    public void deleteLoginDevice(Long memberId, Long id) {
        loginDeviceMapper.deleteLoginDevice(memberId, id);
    }

    @Override
    public LoginDevice findDeviceBySerialNumber(Long memberId, String serialNumber) {
        return DataUtil.copy(loginDeviceMapper.getBySerialNumber(memberId, serialNumber), LoginDevice.class);
    }
}
