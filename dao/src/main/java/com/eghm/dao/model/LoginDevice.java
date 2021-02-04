package com.eghm.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class LoginDevice implements Serializable {
    /**
     * 主键<br>
     * 表 : login_device<br>
     * 对应字段 : id<br>
     */
    private Long id;

    /**
     * 用户id<br>
     * 表 : login_device<br>
     * 对应字段 : user_id<br>
     */
    private Long userId;

    /**
     * 设备唯一序列号<br>
     * 表 : login_device<br>
     * 对应字段 : serial_number<br>
     */
    private String serialNumber;

    /**
     * 设备型号<br>
     * 表 : login_device<br>
     * 对应字段 : device_model<br>
     */
    private String deviceModel;

    /**
     * 登陆ip<br>
     * 表 : login_device<br>
     * 对应字段 : ip<br>
     */
    private Long ip;

    /**
     * 最近一次登陆的时间<br>
     * 表 : login_device<br>
     * 对应字段 : login_time<br>
     */
    private Date loginTime;

    private static final long serialVersionUID = 1L;

}