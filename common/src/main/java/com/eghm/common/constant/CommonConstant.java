package com.eghm.common.constant;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 二哥很猛
 * @date 2018/1/8 14:41
 */
public class CommonConstant {

    private CommonConstant() {
    }

    /**
     * 根节点
     */
    public static final long ROOT = 0;

    /**
     * 系统默认字符集
     */
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * 图形验证码 key
     */
    public static final String IMG_AUTH_CODE = "img_auth_code";

    /**
     * 未知ip地址
     */
    public static final String UNKNOWN = "unknown";

    /**
     * app版本上传目录文件夹名称
     */
    public static final String VERSION = "version";

    /**
     * url分隔符
     */
    public static final String URL_SEPARATOR = "/";

    /**
     * 分页最大值 只适用于移动端
     */
    public static final int MAX_PAGE_SIZE = 20;
}
