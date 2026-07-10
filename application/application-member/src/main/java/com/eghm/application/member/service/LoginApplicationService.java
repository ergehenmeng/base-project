package com.eghm.application.member.service;

import com.eghm.application.member.query.LoginQueryService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.DateUtil;
import com.eghm.domain.member.model.LoginDevice;
import com.eghm.application.shared.dto.ext.LoginRecord;
import com.eghm.application.shared.vo.business.member.LoginDeviceVO;
import com.eghm.domain.member.model.LoginLog;
import com.eghm.domain.member.repository.LoginRepository;
import com.eghm.domain.shared.service.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/3/23
 */
@Service
@AllArgsConstructor
public class LoginApplicationService {
    
    private final LoginRepository loginRepository;
    
    private final LoginQueryService loginQueryService;
    
    private final IdGenerator idGenerator;
    
    /**
     * 添加登陆日志
     * 更新设备登录日志
     *
     * @param loginRecord 登陆日志
     */
    public void insertLoginLog(LoginRecord loginRecord) {
        LoginLog loginLog = DataUtil.copy(loginRecord, LoginLog.class);
        loginRepository.saveLoginLog(loginLog);
        LoginDevice device = DataUtil.copy(loginRecord, LoginDevice.class);
        device.setId(idGenerator.nextId());
        loginRepository.saveOrUpdateDevice(device);
    }

    /**
     * 删除用户的登陆设备(物理删除登陆设备信息表,逻辑删除登陆日志信息)
     *
     * @param memberId 用户id
     * @param id       id
     */
    public void deleteLoginDevice(Long memberId, Long id) {
        loginRepository.deleteLoginDevice(memberId, id);
    }

    /**
     * 查找指定设备是否有登陆日志
     *
     * @param memberId     用户id
     * @param serialNumber 唯一编号
     * @return 登陆日志
     */
    public LoginDevice getBySerialNumber(Long memberId, String serialNumber) {
        return loginRepository.findDeviceBySerialNumber(memberId, serialNumber);
    }

    /**
     * 查询用户所有的登陆设备信息
     *
     * @param memberId memberId
     * @return 登陆设备列表
     */
    public List<LoginDeviceVO> getByMemberId(Long memberId) {
        List<LoginDevice> deviceList = loginQueryService.listDeviceByMemberId(memberId);
        return DataUtil.copy(deviceList, device -> {
            LoginDeviceVO vo = DataUtil.copy(device, LoginDeviceVO.class, "loginTime");
            vo.setLoginTime(DateUtil.formatSimple(device.getLoginTime()));
            return vo;
        });    }
}
