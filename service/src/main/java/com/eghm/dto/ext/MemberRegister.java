package com.eghm.dto.ext;

import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2019/8/19 15:52
 */
@Data
public class MemberRegister {

    /**
     * 微信openId(公众号)
     */
    private String mpOpenId;

    /**
     * 微信openId(小程序)
     */
    private String maOpenId;

    /**
     * unionId
     */
    private String unionId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 头像
     */
    private String avatar;

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
     * 性别 0:未知 1:男 2:女
     */
    private Integer sex;

    /**
     * 注册邀请码
     */
    private String inviteCode;

    /**
     * 注册ip
     */
    private String registerIp;
}
