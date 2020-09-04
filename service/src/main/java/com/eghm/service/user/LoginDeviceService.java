package com.eghm.service.user;

import com.eghm.dao.model.LoginLog;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
public interface LoginDeviceService {

    /**
     * 查找指定设备是否有登陆日志
     * @param userId       用户id
     * @param serialNumber 唯一编号
     * @return 登陆日志
     */
    LoginLog getBySerialNumber(Integer userId, String serialNumber);
}
