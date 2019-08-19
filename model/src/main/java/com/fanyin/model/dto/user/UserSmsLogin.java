package com.fanyin.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/8/19 16:57
 */
@Data
public class UserSmsLogin implements Serializable {

    /**
     * 短信验证码
     */
    private String smsCode;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 请求类型 PC ANDROID IOS,WECHAT
     */
    private String channel;
}
