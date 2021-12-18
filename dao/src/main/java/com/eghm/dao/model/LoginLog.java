package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登陆日志
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class LoginLog extends BaseEntity {

    /**
     * 用户id<br>
     * 表 : login_log<br>
     * 对应字段 : user_id<br>
     */
    private Long userId;

    /**
     * 登陆渠道<br>
     * 表 : login_log<br>
     * 对应字段 : channel<br>
     */
    private String channel;

    /**
     * 登陆ip<br>
     * 表 : login_log<br>
     * 对应字段 : ip<br>
     */
    private Long ip;

    /**
     * 设备厂商<br>
     * 表 : login_log<br>
     * 对应字段 : device_brand<br>
     */
    private String deviceBrand;

    /**
     * 设备型号<br>
     * 表 : login_log<br>
     * 对应字段 : device_model<br>
     */
    private String deviceModel;

    /**
     * 软件版本<br>
     * 表 : login_log<br>
     * 对应字段 : software_version<br>
     */
    private String softwareVersion;

    /**
     * 设备唯一编号<br>
     * 表 : login_log<br>
     * 对应字段 : software_version<br>
     */
    private String serialNumber;

    /**
     * 删除状态 0:未删除 1:已删除<br>
     * 表 : login_log<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

}