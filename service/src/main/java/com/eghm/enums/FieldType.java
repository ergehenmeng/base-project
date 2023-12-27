package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用于指定脱敏字段类型
 * @author 二哥很猛
 * @since 2023/12/27
 */

@Getter
@AllArgsConstructor
public enum FieldType {

    /**
     * 用户id
     */
    USER_ID,

    /**
     * 中文名
     */
    CHINESE_NAME,

    /**
     * 身份证号
     */
    ID_CARD,

    /**
     * 座机号
     */
    FIXED_PHONE,

    /**
     * 手机号
     */
    MOBILE_PHONE,

    /**
     * ADDRESS
     */
    ADDRESS,

    /**
     * 电子邮件
     */
    EMAIL,

    /**
     * 密码
     */
    PASSWORD,

    /**
     * 中国大陆车牌，包含普通车辆、新能源车辆
     */
    CAR_LICENSE,

    /**
     * 银行卡
     */
    BANK_CARD
}
