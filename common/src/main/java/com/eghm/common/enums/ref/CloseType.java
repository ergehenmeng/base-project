package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单关闭类型
 * @author 二哥很猛
 * @date 2022/7/28
 */
@Getter
@AllArgsConstructor
public enum CloseType implements IEnum<Integer> {

    /**
     * 过期自动关闭
     */
    EXPIRE(1, "过期自动关闭"),

    /**
     * 用户取消
     */
    CANCEL(2, "用户取消"),

    /**
     * 退款拒绝
     */
    REFUSE(3, "退款拒绝"),

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
