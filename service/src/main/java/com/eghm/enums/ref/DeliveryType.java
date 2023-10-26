package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 殿小二
 * @date 2022/9/4
 */
@AllArgsConstructor
@Getter
public enum DeliveryType implements EnumBinder {
    
    /**
     * 无须发货
     */
    NO_SHIPMENT(0, "无须发货"),

    /**
     * 快递包邮
     */
    EXPRESS(1, "快递包邮"),

    /**
     * 门店自提
     */
    SELF_PICK(2, "门店自提"),
    ;

    @JsonValue
    @EnumValue
    private final int value;
    
    private final String name;


    @Override
    public String toString() {
        return value + ":" + name;
    }
}
