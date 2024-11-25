package com.eghm.enums;

/**
 * @author 二哥很猛
 * @since 2023/10/10
 */
public interface EnumBinder<T> {

    /**
     * @return 显示到前端的值
     */
    T getValue();

    /**
     * 判断是否匹配指定的枚举值
     *
     * @param value 实际上就是toString的
     * @return 该枚举是否匹配
     */
    boolean match(String value);
}
