package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 *
 * @author 殿小二
 * @since 2020/9/2
 */
@AllArgsConstructor
@Getter
public enum TemplateType {

    /**
     * 未指定短信类型,则为自定义短信:default
     */
    DEFAULT("default", "自定义短信", "", ""),

    /**
     * 登陆发送短信
     */
    MEMBER_LOGIN("member_login", "登陆发送短信", "", ""),

    /**
     * 登陆发送短信
     */
    USER_LOGIN("user_login", "登陆发送短信", "", ""),

    /**
     * 忘记登陆密码
     */
    FORGET("forget", "忘记密码发送短信", "", ""),

    /**
     * 注册发送短信
     */
    REGISTER("register", "注册发送短信", "", ""),

    /**
     * 商户解绑微信号短信
     */
    MERCHANT_UNBIND("merchant_unbind", "商户解绑微信号短信", "", ""),

    /**
     * 更换邮箱发送短信验证码
     */
    CHANGE_EMAIL("change_email", "更换邮箱发送短信验证码", "", ""),

    /**
     * 确认无房,发送通知短信
     */
    CONFIRM_NO_ROOM("confirm_no_room", "确认无房,发送通知短信", "", ""),

    ;
    @EnumValue
    @JsonValue
    private final String value;

    /**
     * 短信名称
     */
    private final String name;

    /**
     * 模板Id
     */
    private final String templateId;

    /**
     * 内容
     */
    private final String content;

    public static TemplateType of(String value) {
        return Arrays.stream(TemplateType.values())
                .filter(map -> value.equals(map.getValue()))
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.SMS_TEMPLATE_NULL));
    }
}
