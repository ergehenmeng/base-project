package com.eghm.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 殿小二
 * @date 2022/5/27
 */
@Getter
@AllArgsConstructor
public enum MerchantState implements IEnum<Integer> {
    
    /**
     * 锁定
     */
    LOCK(0, "锁定"),
    
    /**
     * 正常
     */
    NORMAL(1, "正常"),
    
    /**
     * 注销
     */
    LOGOUT(2, "注销"),
    
    ;
    /**
     * 值
     */
    private final int value;
    
    /**
     * 说明
     */
    private final String name;
    
    @Override
    public Integer getValue() {
        return value;
    }
}
