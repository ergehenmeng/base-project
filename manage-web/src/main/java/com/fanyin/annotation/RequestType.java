package com.fanyin.annotation;

/**
 * 操作类型
 * @author 二哥很猛
 * @date 2019/1/15 17:18
 */
public enum RequestType {
    /**
     * 更新操作
     */
    UPDATE,
    /**
     * 删除操作
     */
    DELETE,
    /**
     * 查询
     */
    SELECT,
    /**
     * 添加操作
     */
    INSERT,

    /**
     * 页面跳转
     */
    PAGE,
    /**
     * 多项操作
     */
    ALL
}
