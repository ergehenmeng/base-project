package com.eghm.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * @author 二哥很猛
 * @since 2018/11/23 15:25
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemConstant {

    /**
     * 全局字符集 utf-8
     */
    public static final String CHARSET = "UTF-8";

    /**
     * 文件相对路径的顶级路径
     */
    public static final String DEFAULT_PATTERN = File.separator + "resource" + File.separator;

}
