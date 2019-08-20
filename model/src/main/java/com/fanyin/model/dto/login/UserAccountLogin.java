package com.fanyin.model.dto.login;

import com.fanyin.model.validation.annotation.RangeLength;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "登陆账号不能为空")
    private String account;

    /**
     * 密码
     */
    @RangeLength(required = true,min = 32,max = 32,message = "密码格式错误")
    private String pwd;

    /**
     * 登陆渠道 {@link com.fanyin.common.enums.Channel}
     */
    private String channel;
}
