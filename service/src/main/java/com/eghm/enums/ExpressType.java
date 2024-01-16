package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2023/11/29
 */

@Getter
@AllArgsConstructor
public enum ExpressType {

    /**
     * 韵达
     */
    YD("yundao", "韵达"),

    /**
     * 中通
     */
    ZT("zhongtong", "中通"),

    /**
     * 华夏
     */
    HX("huanxin", "华夏"),

    /**
     * EMS
     */
    EMS("ems", "EMS"),

    /**
     * DHL
     */
    DHL("dhl", "DHL"),

    /**
     * TNT
     */
    TNT("tnt", "TNT"),

    /**
     * CES
     */
    CES("ces", "CES"),

    /**
     * 菜鸟
     */
    CN("cainiao", "菜鸟"),

    /**
     * 顺丰
     */
    SF("sf", "顺丰"),

    /**
     * 圆通
     */
    YTO("yto", "圆通"),

    /**
     * 其他
     */
    OTHER("other", "其他");

    private final String code;

    private final String name;

    /**
     * 快递公司映射
     *
     * @param code code
     * @return 角色列表
     */
    public static ExpressType of(String code) {
        return Arrays.stream(ExpressType.values())
                .filter(map -> code.equals(map.getCode())).findFirst().orElse(OTHER);
    }
}
