package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class User implements Serializable {

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
     * 总积分数 <br>
     * 表 : user<br>
     * 对应字段 : score<br>
     */
    private Integer score;

    /**
     * 邀请码 <br>
     * 表 : user<br>
     * 对应字段 : invite_code<br>
     */
    private String inviteCode;

    /**
     * 真实姓名<br>
     * 表 : user<br>
     * 对应字段 : real_name<br>
     */
    private String realName;

    /**
     * 身份证号码,前6位加密<br>
     * 表 : user<br>
     * 对应字段 : id_card<br>
     */
    private String idCard;

    /**
     * 生日yyyyMMdd<br>
     * 表 : user<br>
     * 对应字段 : birthday<br>
     */
    private String birthday;

    /**
     * 性别 性别 0:未知 1:男 2:女 <br>
     * 表 : user<br>
     * 对应字段 : sex<br>
     */
    private Byte sex;

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