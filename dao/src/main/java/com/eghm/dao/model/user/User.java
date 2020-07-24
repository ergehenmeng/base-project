package com.eghm.dao.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 * @author 二哥很猛
 */
@Data
public class User implements Serializable {

    /**
     * 主键<br>
     * 表 : user<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 昵称<br>
     * 表 : user<br>
     * 对应字段 : nick_name<br>
     */
    private String nickName;

    /**
     * 手机号码<br>
     * 表 : user<br>
     * 对应字段 : mobile<br>
     */
    private String mobile;

    /**
     * 电子邮箱<br>
     * 表 : user<br>
     * 对应字段 : email<br>
     */
    private String email;

    /**
     * 登陆密码<br>
     * 表 : user<br>
     * 对应字段 : pwd<br>
     */
    private String pwd;

    /**
     * 状态 0:注销 1正常 <br>
     * 表 : user<br>
     * 对应字段 : state<br>
     */
    private Boolean state;

    /**
     * 注册渠道 pc,android,ios,h5,other<br>
     * 表 : user<br>
     * 对应字段 : channel<br>
     */
    private Byte channel;

    /**
     * 头像地址<br>
     * 表 : user_ext<br>
     * 对应字段 : avatar<br>
     */
    private String avatar;

    /**
     * 注册地址<br>
     * 表 : user<br>
     * 对应字段 : register_ip<br>
     */
    private Long registerIp;

    /**
     * 注册时间<br>
     * 表 : user<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : user<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}