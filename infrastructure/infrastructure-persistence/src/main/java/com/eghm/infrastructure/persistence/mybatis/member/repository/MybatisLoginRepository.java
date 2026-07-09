package com.eghm.infrastructure.persistence.mybatis.member.repository;

import com.eghm.domain.member.repository.LoginRepository;
import com.eghm.infrastructure.persistence.mybatis.mapper.LoginDeviceMapper;
import com.eghm.infrastructure.persistence.mybatis.mapper.LoginLogMapper;
import com.eghm.domain.member.model.LoginDevice;
import com.eghm.domain.member.model.LoginLog;
import com.eghm.infrastructure.persistence.mybatis.po.LoginDevicePO;
import com.eghm.infrastructure.persistence.mybatis.po.LoginLogPO;
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
