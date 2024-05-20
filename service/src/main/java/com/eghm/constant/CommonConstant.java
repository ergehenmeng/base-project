package com.eghm.constant;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 二哥很猛
 * @since 2018/1/8 14:41
 */
public class CommonConstant {

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
     * 分页最大值
     */
    public static final int MAX_PAGE_SIZE = 50;

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
    public static final long ASYNC_MSG_EXPIRE = 30;

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
    public static final String SPECIAL_SPLIT = "@";

    /**
     * 核销码刷新频率 60秒
     */
    public static final int MAX_VERIFY_NO_EXPIRE = 60_000;

    /**
     * 签名过期时间 60秒
     */
    public static final long MAX_SIGN_EXPIRE = 60_000;

    /**
     * 最大锁屏时间 1天
     */
    public static final long MAX_LOCK_SCREEN = 86_400;

    /**
     * redis中 limit限制最大时间7天
     */
    public static final long LIMIT_MAX_EXPIRE = 604_800;

    /**
     * Scheduled 定时任务最大锁时间
     */
    public static final long SCHEDULED_MAX_LOCK_TIME = 60;

    /**
     * 最小评分数量
     */
    public static final long MIN_SCORE_NUM = 5;

    /**
     * bitmap最大有效位 此处不使用64位的原因是针对无符号的u64最多只支持63位(最高位是符号位)
     */
    public static final int BITMAP = 32;

    /**
     * 父节点id
     */
    public static final String ROOT_NODE = "0";

    /**
     * 签名key
     */
    public static final String APP_SECRET = "appSecret";

    /**
     * 签名key
     */
    public static final String DATA = "data";

    /**
     * 时间戳
     */
    public static final String TIMESTAMP = "timestamp";

    /**
     * 阿里退款异步回调
     */
    public static final String ALI_REFUND_NOTIFY_URL = "/webapp/notify/ali/refund";

    /**
     * 阿里支付异步回调
     */
    public static final String ALI_PAY_NOTIFY_URL = "/webapp/notify/ali/pay";

    /**
     * 微信退款异步回调
     */
    public static final String WECHAT_REFUND_NOTIFY_URL = "/webapp/notify/weChat/refund";

    /**
     * 微信支付异步回调
     */
    public static final String WECHAT_PAY_NOTIFY_URL = "/webapp/notify/weChat/pay";

    /**
     * 积分充值商品名称
     */
    public static final String SCORE_RECHARGE_GOOD_TITLE = "商户积分充值%s元";

    /**
     * 提现交易单号前缀
     */
    public static final String WITHDRAW_PREFIX = "TX";

    /**
     * 积分提现交易单号前缀
     */
    public static final String SCORE_WITHDRAW_PREFIX = "STX";

    /**
     * 积分充值交易单号前缀
     */
    public static final String SCORE_RECHARGE_PREFIX = "SCZ";

    private CommonConstant() {
    }
}
