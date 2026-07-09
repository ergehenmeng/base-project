package com.eghm.domain.member.valueobject;

import lombok.Data;

/**
 * 会员注册信息.
 *
 * @author 二哥很猛
 * @since 2026/07/09
 */
@Data
public class MemberRegistrationInfo {

    /**
     * 微信openId(公众号)
     */
    private String mpOpenId;

    /**
     * 小程序openId
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
     * 手机号
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
     * 邀请码
     */
    private String inviteCode;

    /**
     * 注册ip
     */
    private String registerIp;
}
