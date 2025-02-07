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
     * 签到积分
     */
    public static final String SIGN_IN_SCORE = "sign_in_score";

    /**
     * 收货地址最大数
     */
    public static final String MEMBER_ADDRESS_MAX = "member_address_max";

    /**
     * ios最新版本号
     */
    public static final String IOS_LATEST_VERSION = "ios_latest_version";

    /**
     * android最新版本号
     */
    public static final String ANDROID_LATEST_VERSION = "android_latest_version";

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
     * 订单完成后多长时间进行系统默认评价
     */
    public static final String ORDER_EVALUATE_TIME = "order_evaluate_time";

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

    /**
     * 匿名头像图片地址
     */
    public static final String ANONYMITY_AVATAR = "anonymity_avatar";

    /**
     * 商户授权码过期时间
     */
    public static final String MERCHANT_AUTH_CODE_EXPIRE = "merchant_auth_code_expire";

    /**
     * 商户授权地址
     */
    public static final String MERCHANT_AUTH_PATH = "merchant_auth_path";

    /**
     * 平台服务费率,单位:%
     */
    public static final String PLATFORM_SERVICE_RATE = "platform_service_rate";

    /**
     * 场馆场次价格单次设置日期最大间隔
     */
    public static final String VENUE_SITE_MAX_DAY = "venue_site_max_day";

    /**
     * 兑换码使用范围(商品范围)
     */
    public static final String REDEEM_CODE_SCOPE = "redeem_code_scope";

    /**
     * 留言被举报多少次自动屏蔽留言
     */
    public static final String COMMENT_REPORT_SHIELD = "comment_report_shield";

    /**
     * 订单(发起仅退款)自动确认退款时间 默认2天
     */
    public static final String ORDER_REFUND_CONFIRM_TIME = "order_refund_confirm_time";

    /**
     * 订单(发起退货退款)自动确认退款时间 默认7天
     */
    public static final String ORDER_RETURN_REFUND_TIME = "order_return_refund_time";

    /**
     * 零售支持售后退款时间(即:订单完成后几天内依旧可以进行售后退款) 默认:7天
     */
    public static final String SUPPORT_AFTER_SALE_TIME = "support_after_sale_time";

    /**
     * 订单完成延迟分账时间(即:订单完成后几天后进行分账) 默认:14天 注意该时间必须大于零售支持售后退款的时间,
     * 因为在售后时间内,即使订单完成依旧可以退款,此次不能太早进行分账
     */
    public static final String DELAY_ROUTING_TIME = "delay_routing_time";

    /**
     * 积分最小充值金额
     */
    public static final String SCORE_MIN_RECHARGE = "score_min_recharge";

    /**
     * 积分最小提现金额
     */
    public static final String SCORE_MIN_WITHDRAW = "score_min_withdraw";

    /**
     * 商户品类销售排行榜
     */
    public static final String PRODUCT_SALE_RANKING = "product_sale_ranking";

    /**
     * 商户销售排行榜
     */
    public static final String MERCHANT_SALE_RANKING = "merchant_sale_ranking";

    /**
     * 微信扫码登录跳转地址
     */
    public static final String WECHAT_REDIRECT_URL = "wechat_redirect_url";

    /**
     * 系统名称
     */
    public static final String SYSTEM_NAME = "system_name";
}
