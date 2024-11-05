package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.LoginRecord;
import com.eghm.dto.business.member.LoginLogQueryRequest;
import com.eghm.model.LoginDevice;
import com.eghm.model.LoginLog;
import com.eghm.vo.member.LoginDeviceVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/3/23
 */
public interface LoginService {

    /**
     * 分页查询登陆日志
     *
     * @param request 分页查询参数
     * @return 登录日志
     */
    Page<LoginLog> getByPage(LoginLogQueryRequest request);

    /**
     * 添加登陆日志
     * 更新设备登录日志
     *
     * @param loginRecord 登陆日志
     */
    void insertLoginLog(LoginRecord loginRecord);

    /**
     * 删除用户的登陆设备(物理删除登陆设备信息表,逻辑删除登陆日志信息)
     *
     * @param memberId 用户id
     * @param id       id
     */
    void deleteLoginDevice(Long memberId, Long id);

    /**
     * 查找指定设备是否有登陆日志
     *
     * @param memberId     用户id
     * @param serialNumber 唯一编号
     * @return 登陆日志
     */
    LoginDevice getBySerialNumber(Long memberId, String serialNumber);

    /**
     * 查询用户所有的登陆设备信息
     *
     * @param memberId memberId
     * @return 登陆设备列表
     */
    List<LoginDeviceVO> getByMemberId(Long memberId);
}
