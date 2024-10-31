package com.eghm.enums;

import java.util.List;

/**
 * 状态机事件枚举
 *
 * @author 二哥很猛
 * @since 2022/11/18
 */
public interface IEvent {

    /**
     * 原状态
     *
     * @return 多对一
     */
    List<Integer> from();

    /**
     * 最终流转状态
     *
     * @return 1
     */
    Integer to();
}
