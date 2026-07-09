package com.eghm.domain.shared.enums;

/**
 * 带值的枚举接口
 * 用于提供类型安全的枚举值访问
 *
 * @param <T> 值的类型
 * @author 二哥很猛
 */
public interface ValuableEnum<T> {

    /**
     * 获取枚举值
     *
     * @return 枚举值
     */
    T getValue();
}
