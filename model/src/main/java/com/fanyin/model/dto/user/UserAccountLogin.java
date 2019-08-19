package com.fanyin.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/19 16:55
 */
@Data
public class UserAccountLogin implements Serializable {

    /**
     * 登陆账号 手机号或邮箱
     */
    private String account;

    /**
     * 密码
     */
    private String pwd;
}
