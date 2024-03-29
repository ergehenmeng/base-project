package com.eghm.common.constant;

/**
 * redis缓存常量
 * @author 二哥很猛
 * @date 2018/1/12 09:39
 */
public class CacheConstant {

    private CacheConstant() {
    }

    /**
     * 系统参数缓存key前缀
     */
    public static final String SYS_CONFIG = "sys_config";

    /**
     * 数据字典缓存key前缀
     */
    public static final String SYS_DICT = "sys_dict";

    /**
     * 审核配置信息
     */
    public static final String AUDIT_CONFIG = "audit_config";

    /**
     * 短信模板
     */
    public static final String SMS_TEMPLATE = "sms_template";

    /**
     * 轮播图
     */
    public static final String BANNER = "banner";

    /**
     * 公告
     */
    public static final String SYS_BULLETIN = "sys_bulletin";

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
    public static final String SYS_ADDRESS = "sys_address";

    /**
     * 站内信模板
     */
    public static final String IN_MAIL_TEMPLATE = "in_mail_template";

    /**
     * 互斥锁
     */
    public static final String MUTEX_LOCK = "mutex_lock::";

    /**
     * 锁屏
     */
    public static final String LOCK_SCREEN = "lock_screen::";

    /**
     * 账号被踢下线
     */
    public static final String FORCE_OFFLINE = "force_offline::";

    /**
     * 任务异步结果
     */
    public static final String ASYNC_RESPONSE = "async_response::";

    /**
     * 用户登录token
     */
    public static final String ACCESS_TOKEN = "access_token::";

    /**
     * 用户登陆刷新token
     */
    public static final String REFRESH_TOKEN = "refresh_token::";

    /**
     * 同一类型短信发送间隔 sms_type_interval::smsType+mobile
     */
    public static final String SMS_TYPE_INTERVAL = "sms_type_interval::";

    /**
     * 同一类型短信单小时总次数 sms_type_hour_limit::smsType+mobile
     */
    public static final String SMS_TYPE_HOUR_LIMIT = "sms_type_hour_limit::";

    /**
     * 同一类型短信一天总次数 sms_type_day_limit::smsType+mobile
     */
    public static final String SMS_TYPE_DAY_LIMIT = "sms_type_day_limit::";

    /**
     * 同一天同手机号最大次数 sms_day::mobile
     */
    public static final String SMS_DAY = "sms_day::";

    /**
     * 用户签到缓存 user_sign_in::userId
     */
    public static final String USER_SIGN_IN = "user_sign_in::";

    /**
     * hash类型 用户邀请信息缓存
     */
    public static final String USER_INVITE_CODE = "user_invite_code";

    /**
     * post提交限制
     */
    public static final String SUBMIT_LIMIT = "submit_limit::%s_%s";

    /**
     * 小程序配置信息
     */
    public static final String APPLET_PAY_CONFIG = "applet_pay_config";

}
