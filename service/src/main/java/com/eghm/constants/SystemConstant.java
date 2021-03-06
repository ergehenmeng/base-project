package com.eghm.constants;

import java.io.File;

/**
 * @author 二哥很猛
 * @date 2018/11/23 15:25
 */
public class SystemConstant {

    private SystemConstant() {

    }

    /**
     * 全局字符集 utf-8
     */
    public static final String CHARSET = "UTF-8";

    /**
     * 文件相对路径的顶级路径
     */
    public static final String DEFAULT_PATTERN = File.separator + "resource" + File.separator;

    /**
     * 默认文件上传时的文件夹名称 /resource/image/yyyyMMdd/default.png
     * @see SystemConstant#DEFAULT_PATTERN
     */
    public static final String DEFAULT_FOLDER = "image";

    /**
     * 默认的缓存占位符
     */
    public static final String CACHE_PLACE_HOLDER = "#";
}
