package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单退款状态
 * @author 二哥很猛
 * @date 2022/7/28
 */
@Getter
@AllArgsConstructor
public enum RefundState implements IEnum<Integer> {

    /**
     * 退款申请中,待审核
     */
    APPLY(1, "退款申请中"),

    /**
     * 退款中
     */
    PROGRESS(2, "退款中"),

    /**
     * 退款拒绝
     */
    REFUSE(3, "退款拒绝"),

    /**
     * 退款成功
     */
    SUCCESS(4, "退款成功"),

    ;
    /**
     * 状态
     */
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @Override
    public Integer getValue() {
        return value;
    }
}
