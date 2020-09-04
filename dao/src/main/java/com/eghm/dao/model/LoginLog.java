package com.eghm.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登陆日志
 * @author 二哥很猛
 */
@Data
public class LoginLog implements Serializable {

    private static final long serialVersionUID = -5894491747743489142L;

    /**
     * 主键<br>
     * 表 : login_log<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 用户id<br>
     * 表 : login_log<br>
     * 对应字段 : user_id<br>
     */
    private Integer userId;

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
     * 登陆时间<br>
     * 表 : login_log<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

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

}