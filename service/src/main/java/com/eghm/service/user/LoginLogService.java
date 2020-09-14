package com.eghm.service.user;

import com.eghm.model.dto.ext.LoginRecord;
import com.eghm.model.vo.user.LoginDeviceVO;

/**
 * @author 二哥很猛
 * @date 2019/8/28 15:24
 */
public interface LoginLogService {

    /**
     * 添加登陆日志
     * @param record 登陆日志
     */
    void addLoginLog(LoginRecord record);

    /**
     * 获取用户最近一次登陆的信息, 如果用户首次登陆系统时结果为空
     * @param userId 用户id
     * @return 登陆信息
     */
    LoginDeviceVO getLastLogin(Integer userId);

    /**
     * 逻辑删除用户登陆信息
     * @param userId 用户id
     * @param serialNumber 设备号
     */
    void deleteLoginLog(Integer userId, String serialNumber);

}
