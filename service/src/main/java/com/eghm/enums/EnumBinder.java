package com.eghm.enums;

/**
 * @author 二哥很猛
 * @since 2023/10/10
 */
public interface EnumBinder<T> {

    /**
     * 传递给前端或者数据库保存的值
     *
     * @return 显示前端的值
     */
    T getValue();

    /**
     * 前端传递过来绑定的值
     *
     * @param value value
     * @return true/false
     */
    boolean match(String value);
}
