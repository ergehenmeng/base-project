package com.eghm.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.unit.DataSize;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 二哥很猛
 * @since 2018/1/8 14:41
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
     * 文件相对路径的顶级路径
     */
    public static final String ROOT_FOLDER = File.separator + "resource" + File.separator;

    /**
     * 未知ip地址
     */
    public static final String UNKNOWN = "unknown";

    /**
     * 两次提交间隔时间,只针对post请求, 单位: 毫秒
     */
    public static final int SUBMIT_INTERVAL = 1000;

    /**
     * 验证码过期时间
     */
    public static final int SMS_CODE_EXPIRE = 600;

    /**
     * 分页最大值
     */
    public static final int MAX_PAGE_SIZE = 50;

    /**
     * geo排序默认10条
     */
    public static final int GET_LIMIT = 10;

    /**
     * 90天密码未修改提示
     */
    public static final int PWD_UPDATE_TIPS = 90;

    /**
     * 连续密码或验证码错误次数, 超过5次则锁定账户一段时间或重新发送验证码
     */
    public static final int MAX_ERROR_NUM = 5;

    /**
     * 分隔符
     */
    public static final String COMMA = ",";

    /**
     * 特殊分隔符
     */
    public static final String SPECIAL_SPLIT = "@";

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
     * websocket消息路径前缀
     */
    public static final String WEBSOCKET_PREFIX = "/websocket";

    /**
     * 管理后台用户ID websocket-key
     */
    public static final String SECURITY_USER = "securityUser";

    /**
     * 管理后台文件上传前缀
     */
    public static final String MANAGE = "manage:";

    /**
     * 移动端文件上传前缀
     */
    public static final String WEBAPP = "webapp:";

    /**
     * 单日上传限制: 默认128M
     */
    public static final DataSize DAY_MAX_UPLOAD = DataSize.ofMegabytes(128);

    /**
     * 验证码
     */
    public static final String CAPTCHA_KEY = "captcha_key";

    /**
     * openId session_key
     */
    public static final String OPEN_ID = "openId";
}
