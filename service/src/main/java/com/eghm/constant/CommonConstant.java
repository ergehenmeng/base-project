package com.eghm.constant;

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
     * 未知ip地址
     */
    public static final String UNKNOWN = "unknown";

    /**
     * 分页最大值 只适用于移动端
     */
    public static final int MAX_PAGE_SIZE = 20;

    /**
     * geo排序默认10条
     */
    public static final int GET_LIMIT = 10;

    /**
     * 分隔符
     */
    public static final String SPLIT = ",";

    /**
     * idea强迫症
     */
    public static final String LIMIT_ONE = " limit 1 ";

    /**
     * 异步消息在MQ中最大存放时间
     */
    public static final long ASYNC_MSG_EXPIRE = 30_000;

    /**
     * 生产环境
     */
    public static final int ENV_PROD = 1;

    /**
     * 开发环境
     */
    public static final int ENV_DEV = 2;

    /**
     * 支付宝支付成功状态
     */
    public static final String ALI_PAY_SUCCESS = "success";

    /**
     * 支付宝支付失败状态
     */
    public static final String ALI_PAY_FAIL = "fail";

    /**
     * 日志id
     */
    public static final String TRACE_ID = "traceId";

    /**
     * 异步下单最大请求次数
     */
    public static final int MAX_ACCESS_NUM = 10;
}
