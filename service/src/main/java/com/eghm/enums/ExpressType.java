package com.eghm.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
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
     * 圆通速递
     */
    YT("yuantong", "圆通速递"),

    /**
     * 申通快递
     */
    ST("shentong", "申通快递"),

    /**
     * 极兔速递
     */
    JT("jtexpress", "极兔速递"),

    /**
     * 中通快递
     */
    ZT("zhongtong", "中通快递"),

    /**
     * 韵达快递
     */
    YD("yunda", "韵达快递"),

    /**
     * 邮政快递
     */
    YZ("youzhengguonei", "邮政快递"),

    /**
     * 顺丰速运
     */
    SF("shunfeng", "顺丰速运"),

    /**
     * 京东物流
     */
    JD("jd", "京东物流"),

    /**
     * EMS
     */
    EMS("ems", "EMS"),

    /**
     * 德邦快递
     */
    DB("debangkuaidi", "德邦快递"),

    /**
     * 百世快递
     */
    BS("huitongkuaidi", "百世快递"),

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
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ExpressType of(String code) {
        return Arrays.stream(ExpressType.values())
                .filter(map -> code.equals(map.getCode())).findFirst().orElse(OTHER);
    }
}
