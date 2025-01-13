package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 映射
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
     * 登陆
     */
    MEMBER_LOGIN("member_login", "登陆", "", "您正在登陆EGHM平台，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 登陆(后台)
     */
    USER_LOGIN("user_login", "登陆(后台)", "", "您正在登陆EGHM管理平台，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 忘记密码
     */
    FORGET("forget", "忘记密码", "", "您正在修改EGHM平台登录密码，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 注册
     */
    REGISTER("register", "注册", "", "您正在注册EGHM平台，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 更换邮箱
     */
    CHANGE_EMAIL("change_email", "更换邮箱", "", "您正在更换EGHM平台账号的邮箱，短信验证码：${param0}。如非本人操作，请忽略此短信");

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

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TemplateType of(@JsonProperty("value") String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(TemplateType.values()).filter(select -> select.value.equals(value)).findFirst().orElse(null);
    }
}
