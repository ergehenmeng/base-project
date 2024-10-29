package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 映射
 *
 * @author 殿小二
 * @since 2020/9/2
 */
@AllArgsConstructor
@Getter
public enum TemplateType implements ValueEnumBinder {

    /**
     * 未指定短信类型,则为自定义短信:default
     */
    DEFAULT("default", "自定义短信", "", ""),

    /**
     * 登陆发送短信
     */
    MEMBER_LOGIN("member_login", "登陆发送短信", "", "您正在登陆EGHM平台，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 登陆发送短信
     */
    USER_LOGIN("user_login", "登陆发送短信", "", "您正在登陆EGHM管理平台，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 忘记登陆密码
     */
    FORGET("forget", "忘记密码发送短信", "", "您正在修改EGHM平台登录密码，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 注册发送短信
     */
    REGISTER("register", "注册发送短信", "", "您正在注册EGHM平台，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 更换邮箱发送短信验证码
     */
    CHANGE_EMAIL("change_email", "更换邮箱发送短信验证码", "", "您正在更换EGHM平台账号的邮箱，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    ;
    @EnumValue
    @JsonValue
    private final String value;

    /**
     * 短信类型
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

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
