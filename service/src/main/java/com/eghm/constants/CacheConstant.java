package com.eghm.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * redis缓存常量
 *
 * @author 二哥很猛
 * @since 2018/1/12 09:39
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CacheConstant {

    /**
     * 默认过期数据 60s
     */
    public static final long DEFAULT_EXPIRE = 60;

    /**
     * 互斥等待时间 10s
     */
    public static final long MUTEX_EXPIRE = 10;

    /**
     * 占位符(默认)
     */
    public static final String PLACE_HOLDER = "#";

    /**
     * 占位符(成功)
     */
    public static final String SUCCESS_PLACE_HOLDER = "@";

    /**
     * 占位符(失败)
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
     * 新设备登录
     */
    public static final String NEW_DEVICE_LOGIN = "new_device_login:";

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
     * 黑名单缓存
     */
    public static final String BLACK_ROSTER = "black_roster";

    /**
     * 邮件模板
     */
    public static final String EMAIL_TEMPLATE = "email_template";

    /**
     * 全国地址
     */
    public static final String SYS_AREA = "sys_area";

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
     * 锁屏状态
     */
    public static final String LOCK_SCREEN = "lock_screen:";

    /**
     * token (移动端用户)
     */
    public static final String TOKEN = "token:";

    /**
     * 会员token映射, 同一个账户不允许登录多个同类型客户端
     */
    public static final String MEMBER_TOKEN_MAPPING = "member_token_mapping";

    /**
     * 刷新token (移动端用户)
     */
    public static final String REFRESH_TOKEN = "refresh_token:";

    /**
     * 同一类型短信发送间隔 sms_type_interval::templateType+mobile
     */
    public static final String SMS_TYPE_INTERVAL = "sms_type_interval:%s:%s";

    /**
     * 同一类型短信单小时总次数 sms_type_hour_limit::templateType+mobile
     */
    public static final String SMS_TYPE_HOUR_LIMIT = "sms_type_hour_limit:%s:%s";

    /**
     * 同一类型短信一天总次数 sms_type_day_limit::templateType+mobile
     */
    public static final String SMS_TYPE_DAY_LIMIT = "sms_type_day_limit:%s:%s";

    /**
     * 同一天ip发送的短信数量 sms_type_day_limit:ip
     */
    public static final String SMS_IP_LIMIT = "sms_ip_limit:";

    /**
     * 同一天同手机号最大次数 sms_day::mobile
     */
    public static final String SMS_DAY = "sms_day:%s";

    /**
     * 用户签到缓存 member_sign_in::memberId
     */
    public static final String MEMBER_SIGN_IN = "member_sign_in:";

    /**
     * post提交限制
     */
    public static final String SUBMIT_LIMIT = "submit_limit:%s_%s";

    /**
     * 全局距离计算
     */
    public static final String GEO_DISTANCE = "geo_distance";

    /**
     * 消息队列异步key
     */
    public static final String MQ_ASYNC_KEY = "mq_async_key:";

    /**
     * 短信前置
     */
    public static final String SMS_PREFIX = "sms:%s:%s";

    /**
     * 短信验证码前置
     */
    public static final String VERIFY_PREFIX = "verify:";

    /**
     * 短信验证码手机号前缀
     */
    public static final String VERIFY_MOBILE_PREFIX = "verify_mobile:";

    /**
     * token (user) 管理后台用户
     */
    public static final String USER_TOKEN = "user_token:";

    /**
     * 用户token映射
     */
    public static final String USER_TOKEN_MAPPING = "user_token_mapping";

    /**
     * 文章点赞
     */
    public static final String NEWS_PRAISE = "news_praise:";

    /**
     * 留言点赞
     */
    public static final String COMMENT_PRAISE = "comment_praise:";

    /**
     * 商品收藏=member_collect:collect_type:collect_id
     */
    public static final String MEMBER_COLLECT = "member_collect:%s:%s";


    /**
     * 微信小程序二维码
     */
    public static final String WECHAT_QRCODE = "wechat_qrcode:";

}
