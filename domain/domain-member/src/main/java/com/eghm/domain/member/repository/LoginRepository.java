package com.eghm.domain.member.repository;

import com.eghm.domain.member.model.LoginDevice;
import com.eghm.domain.member.model.LoginLog;

/**
 * Login device and log repository.
 */
public interface LoginRepository {

    void saveLoginLog(LoginLog loginLog);

    void saveOrUpdateDevice(LoginDevice device);

    void deleteLoginDevice(Long memberId, Long id);

    LoginDevice findDeviceBySerialNumber(Long memberId, String serialNumber);
}
