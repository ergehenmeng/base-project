package com.fanyin.constants;

/**
 * 全局配置信息常量
 * @author 二哥很猛
 * @date 2018/11/17 15:05
 */
public class ConfigConstant {

    /**
     * 操作日志开关
     */
    public static final String OPERATION_LOG_SWITCH = "operation_log_switch";

    /**
     * 系统环境
     */
    public static final String ENV = "env";

    /**
     * 客户端与服务端时间误差,单位ms
     */
    public static final String TIMESTAMP_DEVIATION = "timestamp_deviation";

    /**
     * 发件人
     */
    public static final String SEND_FROM = "send_from";

    /**
     * 是否开启单客户端登陆
     */
    public static final String SSO_OPEN = "sso_open";

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
    public static final String SMS_TYPE_HOUR = "sms_type_hour";

    /**
     * 同一类型短信一天总次数
     */
    public static final String SMS_TYPE_DAY = "sms_type_day";

    /**
     * 同一天同手机号最大次数
     */
    public static final String SMS_DAY = "sms_day";

    /**
     * 公告显示个数限制
     */
    public static final String NOTICE_LIMIT = "notice_limit";
}
