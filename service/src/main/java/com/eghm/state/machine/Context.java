package com.eghm.state.machine;

/**
 * @author 二哥很猛
 * @since 2022/11/18
 */
public interface Context {

    /**
     * 设置原始值
     * @param from 原始
     */
    void setFrom(Integer from);

    /**
     * 设置新状态
     * @param to 新
     */
    void setTo(Integer to);

    /**
     * 原状态
     * @return 原状态
     */
    Integer getFrom();

    /**
     * 新状态
     * @return 新状态
     */
    Integer getTo();
}
