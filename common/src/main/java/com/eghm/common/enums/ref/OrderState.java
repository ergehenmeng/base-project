package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单支付状态
 * @author 二哥很猛
 * @date 2022/7/28
 */
@Getter
@AllArgsConstructor
public enum OrderState implements IEnum<Integer> {

    /**
     * 待支付
     */
    UN_PAY(0, "待支付"),

    /**
     * 支付处理中
     */
    PROGRESS(1, "支付处理中"),

    /**
     * 已支付,待使用
     */
    UN_USED(2, "待使用"),

    /**
     * 已使用 待评价
     */
    USED(3, "已使用"),

    /**
     * 已完成
     */
    COMPLETE(4, "已完成"),

    /**
     * 已关闭
     */
    CLOSE(5, "已关闭"),

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
