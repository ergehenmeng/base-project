package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 殿小二
 * @date 2022/9/4
 */
@AllArgsConstructor
@Getter
public enum DeliveryType implements IEnum<Integer> {
    
    /**
     * 无须发货
     */
    NO_SHIPMENT(0, "无须发货"),

    /**
     * 快递包邮
     */
    EXPRESS(1, "快递包邮"),
    ;
    private final int value;
    
    private final String name;

    
    @Override
    public Integer getValue() {
        return value;
    }
}
