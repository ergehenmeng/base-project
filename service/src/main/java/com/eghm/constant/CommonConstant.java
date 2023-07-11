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
    public static final String DOT_SPLIT = ",";

    /**
     * idea强迫症
     */
    public static final String LIMIT_ONE = " limit 1 ";

    /**
     * 异步消息在MQ中最大存放时间
     */
    public static final long ASYNC_MSG_EXPIRE = 30_000;

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

    /**
     * 特殊分隔符
     */
    public static final String SPECIAL_SPLIT  = "@";

    /**
     * 核销码刷新频率 60秒
     */
    public static final int MAX_VERIFY_NO_EXPIRE = 60_000;

    /**
     * 最大锁屏时间 1天
     */
    public static final long MAX_LOCK_SCREEN = 86_400_000;

    /**
     * redis中 limit限制最大时间7天
     */
    public static final long LIMIT_MAX_EXPIRE = 604_800_000;

    /**
     * Scheduled 定时任务最大锁时间
     */
    public static final long SCHEDULED_MAX_LOCK_TIME = 60_000;

    /**
     * bitmap最大有效位 此处不使用64位的原因是针对无符号的u64最多只支持63位(最高位是符号位)
     */
    public static final int BITMAP = 32;
}
