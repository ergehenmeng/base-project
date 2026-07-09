package com.eghm.domain.member.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户登陆日志
 *
 * @author 二哥很猛
 */
@Data
public class LoginLog {
    /** id主键 */
    private Long id;

    /** 用户id */
    private Long memberId;

    /** 登陆渠道 */
    private String channel;

    /** 登陆ip */
    private Long ip;

    /** 设备厂商 */
    private String deviceBrand;

    /** 设备型号 */
    private String deviceModel;

    /** 软件版本 */
    private String softwareVersion;

    /** 设备唯一编号 */
    private String serialNumber;

    /** 添加时间 */
    private LocalDateTime createTime;
    /** 是否已删除 0:未删除 1:已删除 */
    private Boolean deleted;

    public void initialize(Long memberId, String channel, Long ip, String deviceBrand, String deviceModel,
                           String softwareVersion, String serialNumber) {
        this.memberId = memberId;
        this.channel = channel;
        this.ip = ip;
        this.deviceBrand = deviceBrand;
        this.deviceModel = deviceModel;
        this.softwareVersion = softwareVersion;
        this.serialNumber = serialNumber;
        this.createTime = LocalDateTime.now();
        this.deleted = false;
    }
}
