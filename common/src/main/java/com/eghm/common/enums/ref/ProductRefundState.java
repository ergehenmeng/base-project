package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 普通商品订单退款状态
 * @author 二哥很猛
 * @date 2022/9/9
 */
@AllArgsConstructor
@Getter
public enum ProductRefundState implements IEnum<Integer> {

    /**
     * 初始状态
     */
    INIT(0, "初始状态"),

    /**
     * 已退款
     */
    REFUND(1, "已退款"),

    /**
     * 部分退款
     */
    REBATE(2, "部分退款"),

    ;

    /**
     * 状态
     */
    private final Integer value;

    /**
     * 备注
     */
    private final String name;

    @Override
    public Integer getValue() {
        return null;
    }
}
