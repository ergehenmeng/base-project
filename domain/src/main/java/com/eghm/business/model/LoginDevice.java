package com.eghm.business.model;

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

}
