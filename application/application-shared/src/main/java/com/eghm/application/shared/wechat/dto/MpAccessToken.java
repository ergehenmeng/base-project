package com.eghm.application.shared.wechat.dto;

import lombok.Data;

/**
 * 微信公众号授权凭证.
 *
 * @author 二哥很猛
 */
@Data
public class MpAccessToken {

    /**
     * 公众号openId.
     */
    private String openId;
}
