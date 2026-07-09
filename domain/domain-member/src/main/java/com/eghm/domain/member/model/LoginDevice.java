package com.eghm.domain.member.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
public class LoginDevice {
    /** id主键 */
    private Long id;

    /** 用户id */
    private Long memberId;

    /** 设备唯一序列号 */
    private String serialNumber;

    /** 设备型号 */
    private String deviceModel;

    /** 登陆ip */
    private Long ip;

    /** 最近一次登陆的时间 */
    private LocalDateTime loginTime;

    public void initialize(Long memberId, String serialNumber, String deviceModel, Long ip) {
        this.memberId = memberId;
        this.serialNumber = serialNumber;
        this.deviceModel = deviceModel;
        this.ip = ip;
        this.loginTime = LocalDateTime.now();
    }

    public void updateLoginInfo(Long ip, LocalDateTime loginTime) {
        this.ip = ip;
        this.loginTime = loginTime;
    }

}
