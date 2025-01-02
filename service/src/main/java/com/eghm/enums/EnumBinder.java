package com.eghm.enums;

/**
 * @author 二哥很猛
 * @since 2023/10/10
 */
public interface EnumBinder<T> {

    /**
     * @return 保存数据库或前台使用的值
     */
    T getValue();

    /**
     * 判断前台传递过来的值是否匹配当前的枚举值
     *
     * @param value 值
     * @return true:匹配 false:不匹配
     */
    boolean match(String value);
}
