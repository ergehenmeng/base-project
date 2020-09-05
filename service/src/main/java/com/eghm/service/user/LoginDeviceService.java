package com.eghm.service.user;

import com.eghm.dao.model.LoginDevice;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
public interface LoginDeviceService {

    /**
     * 非空插入
     * @param device device
     */
    void insertOrUpdateSelective(LoginDevice device);

    /**
     * 删除用户的登陆设备(物理删除登陆设备信息表,逻辑删除登陆日志信息)
     * @param userId 用户id
     * @param serialNumber 设备号
     */
    void deleteLoginDevice(Integer userId, String serialNumber);

    /**
     * 查找指定设备是否有登陆日志
     * @param userId       用户id
     * @param serialNumber 唯一编号
     * @return 登陆日志
     */
    LoginDevice getBySerialNumber(Integer userId, String serialNumber);
}
