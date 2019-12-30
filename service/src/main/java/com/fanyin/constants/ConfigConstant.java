package com.fanyin.constants;

/**
 * 全局配置信息常量
 * @author 二哥很猛
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
    public static final String SINGLE_CLIENT_LOGIN = "single_client_login";

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
     * 默认文件上传的文件夹名称
     */
    public static final String DEFAULT_UPLOAD_FOLDER = "default_upload_folder";

    /**
     * 接口验签
     */
    public static final String SIGNATURE_VERIFICATION = "signature_verification";

    /**
     * 安卓软件包大小
     */
    public static final String ANDROID_MAX_SIZE = "android_max_size";

    /**
     * 模拟短信验证码
     */
    public static final String ANALOG_SMS_CODE = "analog_sms_code";

    /**
     * 非注解缓存的默认过期时间
     */
    public static final String CACHE_EXPIRE = "cache_expire:";

    /**
     * 登陆token过期时间
     */
    public static final String TOKEN_EXPIRE = "token_expire:";
}
