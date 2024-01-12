package com.eghm.constant;

/**
 * redis缓存常量
 * @author 二哥很猛
 * @date 2018/1/12 09:39
 */
public class CacheConstant {

    private CacheConstant() {
    }

    /**
     * 默认的缓存占位符(处理中)
     */
    public static final String PLACE_HOLDER = "#";

    /**
     * 占位符(成功)
     */
    public static final String SUCCESS_PLACE_HOLDER = "@";

    /**
     * 占位符(系统异常)
     */
    public static final String ERROR_PLACE_HOLDER = "&";

    /**
     * 系统参数缓存key前缀
     */
    public static final String SYS_CONFIG = "sys_config";

    /**
     * 数据字典缓存key前缀
     */
    public static final String SYS_DICT = "sys_dict";

    /**
     * 短信模板
     */
    public static final String SMS_TEMPLATE = "sms_template";

    /**
     * 敏感词
     */
    public static final String SENSITIVE_WORD = "sensitive_word";

    /**
     * 轮播图
     */
    public static final String BANNER = "banner";

    /**
     * 公告
     */
    public static final String SYS_NOTICE = "sys_notice";

    /**
     * 推送消息模板
     */
    public static final String PUSH_TEMPLATE = "push_template";

    /**
     * 黑名单缓存
     */
    public static final String BLACK_ROSTER = "black_roster";

    /**
     * freemarker模板缓存 主要针对邮件模板
     */
    public static final String FREEMARKER_TEMPLATE = "freemarker_template";

    /**
     * 邮件模板
     */
    public static final String EMAIL_TEMPLATE = "email_template";

    /**
     * 全国地址
     */
    public static final String SYS_AREA = "sys_area";

    /**
     * 全国地址,按pid查询
     */
    public static final String SYS_AREA_PID = "sys_area_pid";

    /**
     * 快递公司
     */
    public static final String EXPRESS = "express";

    /**
     * 全国地址 按id查询
     */
    public static final String SYS_AREA_ID = "sys_area_id";

    /**
     * 第三方授权配置信息
     */
    public static final String AUTH_CONFIG = "auth_config";

    /**
     * 站内信模板
     */
    public static final String IN_MAIL_TEMPLATE = "in_mail_template";

    /**
     * 互斥锁
     */
    public static final String MUTEX_LOCK = "mutex_lock:";

    /**
     * 锁屏状态
     */
    public static final String LOCK_SCREEN = "lock_screen:";

    /**
     * 用户登录token
     */
    public static final String ACCESS_TOKEN = "access_token:";

    /**
     * 会员token映射
     */
    public static final String MEMBER_TOKEN_MAPPING = "member_token_mapping";

    /**
     * 用户登陆刷新token
     */
    public static final String REFRESH_TOKEN = "refresh_token:";

    /**
     * 同一类型短信发送间隔 sms_type_interval::smsType+mobile
     */
    public static final String SMS_TYPE_INTERVAL = "sms_type_interval:";

    /**
     * 同一类型短信单小时总次数 sms_type_hour_limit::smsType+mobile
     */
    public static final String SMS_TYPE_HOUR_LIMIT = "sms_type_hour_limit:";

    /**
     * 同一类型短信一天总次数 sms_type_day_limit::smsType+mobile
     */
    public static final String SMS_TYPE_DAY_LIMIT = "sms_type_day_limit:";

    /**
     * 同一天ip发送的短信数量 sms_type_day_limit:ip
     */
    public static final String SMS_IP_LIMIT = "sms_ip_limit:";

    /**
     * 同一天同手机号最大次数 sms_day::mobile
     */
    public static final String SMS_DAY = "sms_day:";

    /**
     * 用户签到缓存 member_sign_in::memberId
     */
    public static final String MEMBER_SIGN_IN = "member_sign_in:";

    /**
     * post提交限制
     */
    public static final String SUBMIT_LIMIT = "submit_limit:%s_%s";

    /**
     * 零售标签
     */
    public static final String ITEM_TAG = "item_tag";

    /**
     * 图片验证码
     */
    public static final String IMAGE_CAPTCHA = "image_captcha:";

    /**
     * 景区距离计算
     */
    public static final String GEO_SCENIC_DISTANCE = "geo_scenic_distance";

    /**
     * 全局距离计算
     */
    public static final String GEO_DISTANCE = "geo_distance";

    /**
     * 消息队列异步key
     */
    public static final String MQ_ASYNC_KEY = "mq_async_key:";

    /**
     * 消息队列异步数据的key
     */
    public static final String MQ_ASYNC_DATA_KEY = "mq_async_data_key:";

    /**
     * 短信前置
     */
    public static final String SMS_PREFIX = "sms:";

    /**
     * 短信验证码前置
     */
    public static final String VERIFY_PREFIX = "verify:";

    /**
     * 短信验证码手机号前缀
     */
    public static final String VERIFY_MOBILE_PREFIX = "verify_mobile:";

    /**
     * 奖品总数量
     */
    public static final String LOTTERY_PRIZE_NUM = "lottery_prize_num:";

    /**
     * 支付宝支付异步通知锁
     */
    public static final String ALI_PAY_NOTIFY_LOCK = "ali_pay_notify_lock:";

    /**
     * 支付宝退款异步通知锁
     */
    public static final String ALI_REFUND_NOTIFY_LOCK = "ali_refund_notify_lock:";

    /**
     * 微信支付异步通知锁
     */
    public static final String WECHAT_PAY_NOTIFY_LOCK = "wechat_pay_notify_lock:";

    /**
     * 微信退款异步通知锁
     */
    public static final String WECHAT_REFUND_NOTIFY_LOCK = "wechat_refund_notify_lock:";

    /**
     * 管理后台手动退款锁
     */
    public static final String MANUAL_REFUND_LOCK = "manual_refund_lock:";

    /**
     * 发货
     */
    public static final String SIPPING_LOCK = "sipping_lock:";

    /**
     * 缓存
     */
    public static final String USER_TOKEN = "user_token:";

    /**
     * 用户token映射
     */
    public static final String USER_TOKEN_MAPPING = "user_token_mapping";

    /**
     * 抽奖
     */
    public static final String LOTTERY_LOCK = "lottery_lock:%s:%s";

    /**
     * 文章点赞
     */
    public static final String NEWS_GIVE_LIKE = "news_give_like:";

    /**
     * 商品收藏=member_collect:collect_type:collect_id
     */
    public static final String MEMBER_COLLECT = "member_collect:%s:%s";

    /**
     * 留言点赞
     */
    public static final String COMMENT_GIVE_LIKE = "comment_give_like:";
}
