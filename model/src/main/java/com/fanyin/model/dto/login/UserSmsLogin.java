package com.fanyin.model.dto.login;

import com.fanyin.model.validation.annotation.Mobile;
import com.fanyin.model.validation.annotation.RangeLength;
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
    @RangeLength(required = true,min = 4,max = 6,message = "短信验证码格式错误")
    private String smsCode;

    /**
     * 手机号
     */
    @Mobile(required = true)
    private String mobile;

    /**
     * 登陆渠道 {@link com.fanyin.common.enums.Channel}
     */
    private String channel;
}