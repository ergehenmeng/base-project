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
     * 操作日志开关
     */
    public static final String OPERATION_LOG_SWITCH = "operation_log_switch";

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
    public static final String FILE_SERVER_ADDRESS = "file_server_address";

    /**
     * 单文件上传大小限制
     */
    public static final String SINGLE_MAX_FILE_SIZE = "single_max_file_size";

    /**
     * 非注解缓存的默认过期时间
     */
    public static final String CACHE_EXPIRE = "cache_expire";

    /**
     * 空数据(占位数据)默认过期时间
     */
    public static final String NULL_EXPIRE = "null_expire";

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
     * ios最新版本号
     */
    public static final String IOS_LATEST_VERSION = "ios_latest_version";

    /**
     * android最新版本号
     */
    public static final String ANDROID_LATEST_VERSION = "android_latest_version";

    /**
     * 登录方式 1:密码登录 2:短信登录 4:密码+短信登录(必须密码+短信验证码同时登陆) 8:二维码登录(未实现)
     * 注意: 支持异或操作 3:表示密码+短信登录 11:表示密码+短信+二维码登录, 但是由于4和1,2功能上是互斥的,因此不能同时存在
     * 同时二维码登录不能独立存在,必须和124组合使用(因为首次需要绑定微信号)
     */
    public static final String LOGIN_TYPE = "login_type";

    /**
     * 微信扫码登录跳转地址
     */
    public static final String WECHAT_REDIRECT_URL = "wechat_redirect_url";

    /**
     * 管理系统名称
     */
    public static final String SYSTEM_NAME = "system_name";
}
