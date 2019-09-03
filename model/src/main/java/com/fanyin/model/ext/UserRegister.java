package com.fanyin.model.ext;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/19 15:52
 */
@Data
public class UserRegister implements Serializable {

    private static final long serialVersionUID = 1211247555372353757L;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 注册渠道
     */
    private String channel;

    /**
     * 注册ip
     */
    private String registerIp;
}
