package com.eghm.constants;

/**
 * 全局配置信息常量
 * @author 二哥很猛
 */
public class ConfigConstant {

    private ConfigConstant() {
    }

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
     * 公告显示个数限制
     */
    public static final String NOTICE_LIMIT = "notice_limit";

    /**
     * app store下载地址
     */
    public static final String APP_STORE_URL = "app_store_url";

    /**
     * 文件服务器地址
     */
    public static final String FILE_SERVER_ADDRESS = "file_server_address";

    /**
     * 单文件上传大小限制
     */
    public static final String SINGLE_MAX_FILE_SIZE = "single_max_file_size";

    /**
     * 多文件上传大小限制
     */
    public static final String BATCH_MAX_FILE_SIZE = "batch_max_file_size";

    /**
     * 安卓软件包大小
     */
    public static final String ANDROID_MAX_SIZE = "android_max_size";

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
     * jwt秘钥
     */
    public static final String JWT_SECRET_KEY = "jwt_secret_key";

    /**
     * 验证码过期时间
     */
    public static final String AUTH_CODE_EXPIRE = "auth_code_expire";
}
