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
     * 无须发货
     */
    TAKE_SELF(1, "门店自提"),
    
    /**
     * 快递包邮
     */
    express(2, "快递包邮"),
    ;
    private final int value;
    
    private final String name;
    ;
    
    @Override
    public Integer getValue() {
        return value;
    }
}
