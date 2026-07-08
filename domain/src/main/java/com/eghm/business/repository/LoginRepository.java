package com.eghm.business.repository;

import com.eghm.business.model.LoginDevice;
import com.eghm.business.model.LoginLog;

/**
 * Login device and log repository.
 */
public interface LoginRepository {

    void saveLoginLog(LoginLog loginLog);

    void saveOrUpdateDevice(LoginDevice device);

    void deleteLoginDevice(Long memberId, Long id);

    LoginDevice findDeviceBySerialNumber(Long memberId, String serialNumber);
}
