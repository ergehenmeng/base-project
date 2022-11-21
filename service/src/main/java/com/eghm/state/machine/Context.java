package com.eghm.state.machine;

/**
 * @author 二哥很猛
 * @since 2022/11/18
 */
public interface Context {

    /**
     * 原状态
     * @return 原状态
     */
    Integer from();

    /**
     * 新状态
     * @return 新状态
     */
    Integer to();
}
