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
     * 全局所有验证码默认过期时间
     */
    public static final String AUTH_CODE_EXPIRE = "auth_code_expire";

    /**
     * 签到积分
     */
    public static final String SIGN_IN_SCORE = "sign_in_score";

    /**
     * 收货地址最大数
     */
    public static final String USER_ADDRESS_MAX = "user_address_max";

    /**
     * ios最新版本号
     */
    public static final String IOS_LATEST_VERSION = "ios_latest_version";

    /**
     * android最新版本号
     */
    public static final String ANDROID_LATEST_VERSION = "android_latest_version";
    
    /**
     * 商户默认密码
     */
    public static final String MERCHANT_PWD = "merchant_pwd";

    /**
     * 房态单次设置日期最大间隔
     */
    public static final String ROOM_CONFIG_MAX_DAY = "room_config_max_day";

    /**
     * 线路单次设置日期最大间隔
     */
    public static final String LINE_CONFIG_MAX_DAY = "line_config_max_day";

    /**
     * 活动日期最大间隔
     */
    public static final String ACTIVITY_CONFIG_MAX_DAY = "activity_config_max_day";

    /**
     * 是否包含景区距离字段
     */
    public static final String SCENIC_CONTAIN_DISTANCE = "scenic_contain_distance";

    /**
     * 购物车数量最大值
     */
    public static final String SHOPPING_CAR_MAX = "shopping_car_max";

    /**
     * 订单过期时间
     */
    public static final String ORDER_EXPIRE_TIME = "order_expire_time";

    /**
     * 订单完成时间
     */
    public static final String ORDER_COMPLETE_TIME = "order_complete_time";

    /**
     * 景区活动日期限制
     */
    public static final String SCENIC_ACTIVITY_LIMIT = "scenic_activity_limit";

    /**
     * 线路可以预约的最长时间
     */
    public static final String LINE_MAX_DAY = "line_max_day";

    /**
     * 民宿最大预定时间维度
     */
    public static final String HOMESTAY_MAX_RESERVE_DAY = "homestay_max_reserve_day";

    /**
     * 房型最大推荐数量
     */
    public static final String HOMESTAY_ROOM_MAX_RECOMMEND = "homestay_room_max_recommend";

    /**
     * 单店铺商品最大推荐数量
     */
    public static final String STORE_ITEM_MAX_RECOMMEND = "store_item_max_recommend";

    /**
     * 首页商品最大推荐数量
     */
    public static final String ITEM_MAX_RECOMMEND = "item_max_recommend";

    /**
     * 首页店铺最大推荐数量
     */
    public static final String STORE_MAX_RECOMMEND = "store_max_recommend";
}
