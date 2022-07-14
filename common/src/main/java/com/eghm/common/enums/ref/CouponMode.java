package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 优惠券领取方式
 * @author 二哥很猛
 * @date 2022/7/14
 */
@Getter
@AllArgsConstructor
public enum CouponMode implements IEnum<Integer>  {

    /**
     * 页面领取
     */
    PAGE_RECEIVE(1, "页面领取"),

    /**
     * 手动方法
     */
    GRANT(2, "手动方法");

    /**
     * 方式
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
