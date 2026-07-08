package com.eghm.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 全局配置信息常量
 *
 * @author 二哥很猛
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigConstant {

    /**
     * 支付或退款回调地址
     */
    public static final String PAY_NOTIFY_HOST = "pay_notify_host";

    /**
     * 留言被举报多少次自动屏蔽留言
     */
    public static final String COMMENT_REPORT_SHIELD = "comment_report_shield";

    /**
     * 昵称前缀
     */
    public static final String NICK_NAME_PREFIX = "nick_name_prefix";

    /**
     * 同一类型短信发送间隔
     */
    public static final String SMS_TYPE_INTERVAL = "sms_type_interval";

    /**
     * 同一类型短信单小时总次数
     */
    public static final String SMS_TYPE_HOUR_LIMIT = "sms_type_hour_limit";

    /**
     * 同一类型短信一天总次数
     */
    public static final String SMS_TYPE_DAY_LIMIT = "sms_type_day_limit";

    /**
     * 同一天同手机号最大次数
     */
    public static final String SMS_DAY_LIMIT = "sms_day_limit";

    /**
     * 短信ip限制
     */
    public static final String SMS_IP_LIMIT = "sms_ip_limit";

    /**
     * 公告显示个数限制
     */
    public static final String NOTICE_LIMIT = "notice_limit";

    /**
     * 文件服务器地址
     */
    public static final String FILE_SERVER_HOST = "file_server_host";

    /**
     * 单文件上传大小限制
     */
    public static final String SINGLE_MAX_FILE_SIZE = "single_max_file_size";

    /**
     * 非注解缓存的默认过期时间
     */
    public static final String CACHE_EXPIRE = "cache_expire";

    /**
     * 订单过期时间
     */
    public static final String ORDER_EXPIRE_TIME = "order_expire_time";

    /**
     * 登陆token过期时间
     */
    public static final String TOKEN_EXPIRE = "token_expire";

    /**
     * 刷新token过期时间(比登陆token过期时间要大)
     */
    public static final String REFRESH_TOKEN_EXPIRE = "refresh_token_expire";

    /**
     * 全局所有验证码默认过期时间
     */
    public static final String AUTH_CODE_EXPIRE = "auth_code_expire";

    /**
     * 是否开启双因子认证
     */
    public static final String OPEN_TOTP = "open_totp";

    /**
     * 微信扫码登录跳转地址
     */
    public static final String WECHAT_REDIRECT_URL = "wechat_redirect_url";

    /**
     * 管理系统名称
     */
    public static final String SYSTEM_NAME = "system_name";

    /**
     * 验证码类型
     */
    public static final String CAPTCHA_TYPE = "captcha_type";

    /**
     * 签到积分
     */
    public static final String SIGN_IN_SCORE = "sign_in_score";
}
