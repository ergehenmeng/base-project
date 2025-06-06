package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
     * 登录(C端)
     */
    MEMBER_LOGIN("member_login", "登录(C端)", "", "您正在登陆EGHM平台，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 登录(后台)
     */
    USER_LOGIN("user_login", "登录(后台)", "", "您正在登陆EGHM管理平台，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 忘记密码(C端)
     */
    FORGET("forget", "忘记密码(C端)", "", "您正在修改EGHM平台登录密码，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 注册(C端)
     */
    REGISTER("register", "注册(C端)", "", "您正在注册EGHM平台会员，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 商户解绑微信号
     */
    MERCHANT_UNBIND("merchant_unbind", "商户解绑微信号", "", "您正在解绑商户微信号，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 更换邮箱(C端)
     */
    CHANGE_EMAIL("change_email", "更换邮箱(C端)", "", "您正在更换账号邮箱，短信验证码：${param0}。如非本人操作，请忽略此短信"),

    /**
     * 确认无房
     */
    CONFIRM_NO_ROOM("confirm_no_room", "确认无房", "", "您预订${param0}的房型，经确认该房型已售罄，已做退单处理。订单号：￥{param1}"),

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

    @JsonCreator
    public static TemplateType of(@JsonProperty("value") String value) {
        return Arrays.stream(TemplateType.values())
                .filter(map -> value.equals(map.getValue()))
                .findFirst().orElse(null);
    }
}
