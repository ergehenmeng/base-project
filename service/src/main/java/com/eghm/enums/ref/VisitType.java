package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.eghm.enums.EnumBinder;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Getter
@AllArgsConstructor
public enum VisitType implements EnumBinder {

    /**
     * 首页
     */
    HOME_PAGE(1, "首页"),

    /**
     * 个人中心
     */
    SELF_CENTER(2, "个人中心"),

    /**
     * 商品列表
     */
    PRODUCT_LIST(3, "商品列表"),

    /**
     * 商品详情
     */
    PRODUCT_DETAIL(4, "商品详情"),

    /**
     * 购物车
     */
    PRODUCT_CART(5, "购物车"),

    /**
     * 营销中心
     */
    MARKETING(6, "营销中心"),

    /**
     * 活动中心
     */
    ACTIVITY(7, "活动中心"),

    /**
     * 公告/资讯
     */
    NOTICE_NEWS(8, "公告资讯");

    /**
     * 类型
     */
    @EnumValue
    @JsonValue
    private final int value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static VisitType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(VisitType.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
