package com.eghm.domain.shared.enums;

import com.eghm.annotation.ExcelDesc;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Getter
@AllArgsConstructor
public enum CollectType implements ValuableEnum<Integer> {

    /**
     * 资讯
     */
    NEWS(1, "资讯"),

    /**
     * 公告
     */
    NOTICE(2, "公告");

    /**
     * 状态
     */
    private final Integer value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;
    public static CollectType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(CollectType.values()).filter(couponMode -> couponMode.value == value).findFirst().orElse(null);
    }

}
