package com.eghm.common.constant;

/**
 * redis缓存常量
 * @author 二哥很猛
 * @date 2018/1/12 09:39
 */
public class CacheConstant {

    /**
     * 系统参数缓存key前缀
     */
    public static final String SYSTEM_CONFIG = "system_config";

    /**
     * 数据字典缓存key前缀
     */
    public static final String SYSTEM_DICT = "system_dict";


    /**
     * 任务异步结果
     */
    public static final String ASYNC_RESPONSE = "async_response::";

    /**
     * 用户登录token
     */
    public static final String ACCESS_TOKEN = "access_token::";

    /**
     * 同一类型短信发送间隔
     */
    public static final String SMS_TYPE_INTERVAL = "sms_type_interval::";

    /**
     * 同一类型短信单小时总次数
     */
    public static final String SMS_TYPE_HOUR = "sms_type_hour::";

    /**
     * 同一类型短信一天总次数
     */
    public static final String SMS_TYPE_DAY = "sms_type_day::";

    /**
     * 同一天同手机号最大次数
     */
    public static final String SMS_DAY = "sms_day::";

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
    public static final String SYSTEM_NOTICE = "system_notice";

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

}
