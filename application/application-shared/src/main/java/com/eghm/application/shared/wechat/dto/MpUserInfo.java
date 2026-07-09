package com.eghm.application.shared.wechat.dto;

import lombok.Data;

/**
 * 微信公众号授权用户信息.
 *
 * @author 二哥很猛
 */
@Data
public class MpUserInfo {

    /**
     * 公众号openId.
     */
    private String openId;

    /**
     * unionId.
     */
    private String unionId;

    /**
     * 昵称.
     */
    private String nickname;

    /**
     * 头像.
     */
    private String headImgUrl;

    /**
     * 性别.
     */
    private Integer sex;
}
