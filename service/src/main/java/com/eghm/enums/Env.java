package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统运行环境
 *
 * @author 二哥很猛
 * @since 2023/7/11
 */
@Getter
@AllArgsConstructor
public enum Env {

    /**
     * 生产环境
     */
    PROD,

    /**
     * 测试环境
     */
    TEST,

    /**
     * 开发环境
     */
    DEV
}
