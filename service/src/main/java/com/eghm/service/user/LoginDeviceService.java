package com.eghm.service.user;

import com.eghm.model.LoginDevice;
import com.eghm.model.vo.user.LoginDeviceVO;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
public interface LoginDeviceService {

    /**
     * 插入或更新,更新或插入字段中必须包含唯一性约束条件
     * @param device device
     */
    void insertOrUpdateSelective(LoginDevice device);

    /**
     * 删除用户的登陆设备(物理删除登陆设备信息表,逻辑删除登陆日志信息)
     * @param userId 用户id
     * @param serialNumber 设备号
     */
    void deleteLoginDevice(Long userId, String serialNumber);

    /**
     * 查找指定设备是否有登陆日志
     * @param userId       用户id
     * @param serialNumber 唯一编号
     * @return 登陆日志
     */
    LoginDevice getBySerialNumber(Long userId, String serialNumber);

    /**
     * 查询用户所有的登陆设备信息
     * @param userId userId
     * @return 登陆设备列表
     */
    List<LoginDeviceVO> getByUserId(Long userId);
}
