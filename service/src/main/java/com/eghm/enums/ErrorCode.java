package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统错误信息枚举 包含系统所以的异常信息
 * 404 500等标准错误码返回前台时,可做特殊处理(多个业务共用一个错误码)
 * 1000+ 数据异常
 * 2000+ 业务异常
 * @author 二哥很猛
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {

    /**
     * 点击太快啦~~
     */
    SUBMIT_FREQUENTLY(400, "操作太快啦~~"),

    /**
     * 木有访问的权限
     */
    ACCESS_DENIED(403,"木有访问的权限"),

    /**
     * 用户登陆已过期(后台系统使用)
     */
    LOGIN_EXPIRE(402,"用户登陆已过期"),

    /**
     * 木有找到请求地址
     */
    PAGE_NOT_FOUND(404,"木有找到请求地址"),

    /**
     * 暂无访问权限 (黑名单拦截)
     */
    SYSTEM_AUTH(405,"暂无访问权限"),

    /**
     * 系统繁忙,请稍后再试
     */
    SYSTEM_ERROR(500,"系统繁忙,请稍后再试"),
    
    /**
     * 线程中断异常
     */
    THREAD_INTERRUPTED(501,"线程中断异常"),

    /**
     * 应用上下文尚未完全启动
     */
    SPRING_ON_LOADING(505,"Spring上下文尚未完全启动"),

    /**
     * 该访问地址不支持%s请求
     */
    METHOD_NOT_SUPPORTED(505,"该访问地址不支持%s请求"),

    /**
     * 屏幕锁定中
     */
    LOCK_SCREEN(999, "屏幕锁定中"),

    /**
     * 数据转换异常
     */
    JSON_FORMAT_ERROR(1000,"数据转换异常"),

    /**
     * pbe加密失败
     */
    ENCRYPT_ERROR(1001,"数据加密失败"),

    /**
     * 字符集转换错误
     */
    CHARSET_CONVERT_ERROR(1002,"字符集转换错误"),

    /**
     * 数据解密失败
     */
    DECRYPT_ERROR(1003,"数据解密失败"),

    /**
     *数据加解密失败
     */
    ENCRYPT_DECRYPT_ERROR(1004,"数据加解密失败"),

    /**
     * 对象转换异常
     */
    BEAN_COPY_ERROR(1005,"对象转换异常"),

    /**
     * 数据加密异常,请联系管理人员
     */
    SHA_256_ERROR(1006,"数据加密异常"),

    /**
     * 验签异常
     */
    SIGN_NULL_ERROR(1007,"验签异常"),

    /**
     * 验签失败
     */
    SIGN_ERROR(1008,"验签失败"),

    /**
     * 未指定方法实现
     */
    NOT_IMPLEMENT(1009,"未指定方法实现"),

    /**
     * 参数转换异常
     */
    PARAMETER_CASE_ERROR(1010,"参数转换异常"),

    /**
     *未知主机名
     */
    UN_KNOW_HOSTNAME(1011,"未知主机名"),

    /**
     * 未知主机地址
     */
    UN_KNOW_HOST_ADDRESS(1012,"未知主机地址"),

    /**
     * 请求接口非法
     */
    REQUEST_INTERFACE_ERROR(1013,"请求接口非法"),

    /**
     * 请求参数非法
     */
    REQUEST_PARAM_ILLEGAL(1014,"请求参数非法"),

    /**
     * 请求解析异常
     */
    REQUEST_RESOLVE_ERROR(1015,"请求解析异常"),

    /**
     * 签名信息为空
     */
    SIGNATURE_ERROR(1016,"签名信息为空"),

    /**
     * 时间戳为空
     */
    SIGNATURE_TIMESTAMP_NULL(1016,"数字签名为空"),

    /**
     * 请求已过期
     */
    SIGNATURE_TIMESTAMP_ERROR(1017,"请求已过期"),

    /**
     * 签名校验异常
     */
    SIGNATURE_VERIFY_ERROR(1018,"签名校验失败"),

    /**
     * 读取请求参数异常
     */
    READ_PARAM_ERROR(1019,"读取请求参数异常"),

    /**
     * 非法操作 操作了不属于自己的数据
     */
    ILLEGAL_OPERATION(1020,"非法操作"),

    /**
     * 转换器不支持非枚举类
     */
    ENUM_SUPPORTED(1025,"转换器不支持非枚举类"),

    /**
     * 定时任务配置异常
     */
    TASK_CONFIG_ERROR(1030,"定时任务配置异常"),

    /**
     * 任务未查询到
     */
    TASK_NULL_ERROR(1031,"任务未查询到"),

    /**
     * 数据权限不匹配
     */
    DATA_TYPE_ERROR(1033,"数据权限不匹配"),

    /**
     * 参数解析异常
     */
    PARAM_VERIFY_ERROR(2000,"%s"),

    /**
     * 身份证格式错误
     */
    ID_CARD_ERROR(2001,"身份证格式错误"),

    /**
     * 对象不能为空
     */
    NOT_NULL_ERROR(2002,"对象不能为空"),

    /**
     * select指定nid不能为空
     */
    NID_IS_NULL(2003,"@select指定nid不能为空"),

    /**
     * 权限校验异常,请联系管理人员
     */
    PERMISSION_ERROR(2004,"权限校验异常,请联系管理人员"),

    /**
     * 日志异常,用户未登陆
     */
    OPERATION_LOGIN_ERROR(2005,"日志异常,用户未登陆"),

    /**
     * auth指定的nid不能为空
     */
    AUTH_NID_ERROR(2006,"@auth指定的nid不能为空"),

    /**
     * 日期格式转换异常
     */
    DATE_CASE_ERROR(2007,"日期格式转换异常"),

    /**
     * 模板解析失败
     */
    TEMPLATE_RENDER_ERROR(2008,"模板解析失败"),

    /**
     * 手机号码被占用
     */
    MOBILE_REDO(2010, "手机号码被占用"),

    /**
     * 系统默认角色,禁止删除
     */
    ROLE_FORBID_DELETE(2013, "系统默认角色,禁止删除"),

    /**
     * 角色名称重复
     */
    ROLE_NAME_REDO(2015, "角色名称重复"),

    /**
     * ip格式错误
     */
    IP_ILLEGAL(2030, "ip格式错误"),

    /**
     * 系统配置信息未查询到
     */
    CONFIG_NOT_FOUND_ERROR(3000,"系统配置信息未查询到"),

    /**
     * 系统配置信息更新失败
     */
    UPDATE_CONFIG_ERROR(3001,"系统配置信息更新失败"),

    /**
     * 菜单信息更新失败
     */
    UPDATE_MENU_ERROR(3002,"菜单信息更新失败"),

    /**
     * 验证码输入错误
     */
    IMAGE_CODE_ERROR(3003,"验证码输入错误"),

    /**
     * 账户或密码错误
     */
    ACCOUNT_PASSWORD_ERROR(3004,"账户或密码输入错误"),

    /**
     * 用户信息不存在
     */
    USER_NOT_FOUND(3005,"用户信息不存在"),

    /**
     * 用户已锁定,请联系管理人员
     */
    USER_LOCKED_ERROR(3006,"用户已锁定,请联系管理人员"),

    /**
     * 用户超时,请重新登陆
     */
    USER_TIMEOUT(3007,"用户超时,请重新登陆"),

    /**
     * 登陆信息为空,请重新登陆
     */
    ACCESS_TOKEN_NULL(3008, "登陆信息为空,请重新登陆"),

    /**
     * 用户超时,请重新登陆(用户token校验失败)
     */
    ACCESS_TOKEN_TIMEOUT(3008,"登陆已过期,请重新登陆"),

    /**
     * 用户登陆过期,请重新登陆 (获取用户id失败)
     */
    MEMBER_LOGIN_TIMEOUT(3009,"用户登陆过期,请重新登陆"),

    /**
     * 旧密码输入错误
     */
    USER_PASSWORD_ERROR(3010,"旧密码输入错误"),

    /**
     * 上传文件过大
     */
    UPLOAD_TOO_BIG(3011,"上传文件过大"),

    /**
     * 文件保存失败
     */
    FILE_SAVE_ERROR(3012,"文件保存失败"),

    /**
     * 创建文件失败
     */
    FILE_CREATE_ERROR(3013,"创建文件失败"),

    /**
     * 该数据字典已锁定
     */
    DICT_LOCKED_ERROR(3014,"该数据字典已锁定"),

    /**
     * 同级菜单最多90个
     */
    MENU_MAX_ERROR(3015,"同级菜单最多90个"),

    /**
     * 用户已在其他设备上登陆
     */
    MULTIPLE_CLIENT_LOGIN(3016,"用户已在其他设备上登陆"),

    /**
     * 用户不存在或已冻结
     */
    MEMBER_NOT_FOUND(3017,"用户不存在或已冻结"),

    /**
     * 账号或密码错误
     */
    PASSWORD_ERROR(3018,"账号或密码错误"),

    /**
     * 验证码已过期
     */
    LOGIN_SMS_CODE_EXPIRE(3019,"验证码已过期"),

    /**
     * 短信验证码错误
     */
    LOGIN_SMS_CODE_ERROR(3020,"验证码输入错误"),

    /**
     * 手机号未注册
     */
    MOBILE_NOT_REGISTER(3021,"手机号未注册"),

    /**
     * 短信发送频率太快
     */
    SMS_FREQUENCY_FAST(3022,"短信发送频率太快"),

    /**
     * 单位小时内短信发送上限
     */
    SMS_HOUR_LIMIT(3023,"单位小时内短信发送上限"),

    /**
     * 当天内短信发送上限(统一类型短信)
     */
    SMS_DAY_LIMIT(3024,"当天内短信发送上限"),

    /**
     * 当天内短信发送上限(统一手机号短信)
     */
    MOBILE_DAY_LIMIT(3025,"当天内短信发送上限"),

    /**
     * 版本号重复
     */
    VERSION_REDO(3025,"版本号重复"),

    /**
     * 该手机号已注册
     */
    MOBILE_REGISTER_REDO(3026,"该手机号已注册"),

    /**
     * cron表达式配置错误
     */
    CRON_CONFIG_ERROR(3027,"cron表达式配置错误"),

    /**
     * 按钮菜单无法添加子菜单
     */
    SUB_MENU_ERROR(3027,"按钮菜单无法添加子菜单"),

    /**
     * 内容不能为空
     */
    TEXT_CONTENT_EMPTY(3028,"内容不能为空"),

    /**
     * 新设备登陆校验
     */
    NEW_DEVICE_LOGIN(3029,"新设备登陆校验"),

    /**
     * 账号在其他设备上登陆
     */
    KICK_OFF_LINE(3030,"账号在其他设备上登陆"),

    /**
     * 邮件服务可能未配置
     */
    MAIL_NOT_CONFIG(3031,"邮件服务可能未配置"),

    /**
     * 部门层级已上限
     */
    DEPARTMENT_DEPTH_ERROR(3032,"部门层级已上限"),

    /**
     * 邮件模板未配置
     */
    EMAIL_TEMPLATE_NULL(3045,"邮件模板未配置"),

    /**
     * 请勿重复绑定邮箱
     */
    EMAIL_REDO_BIND(3048,"请勿重复绑定邮箱"),

    /**
     * 请先获取邮箱验证码
     */
    EMAIL_ADDRESS_ERROR(3050,"请先获取邮箱验证码"),

    /**
     * 邮箱验证码输入错误
     */
    EMAIL_CODE_ERROR(3050,"邮箱验证码输入错误"),

    /**
     * 邮箱已被占用
     */
    EMAIL_OCCUPY_ERROR(3053,"邮箱已被占用"),

    /**
     * 请先绑定手机号
     */
    MOBILE_NOT_BIND(3055,"请先绑定手机号"),

    /**
     * 请勿重复签到
     */
    SIGN_IN_REDO(3058,"请勿重复签到"),

    /**
     * 数据格式异常
     */
    DATA_ERROR(3059,"数据格式异常"),

    /**
     * 用户地址未查询到
     */
    MEMBER_ADDRESS_NULL(3062, "用户地址未查询到"),

    /**
     * 省份异常
     */
    PROVINCE_ERROR(3066, "省份异常"),

    /**
     * 城市异常
     */
    CITY_ERROR(3069, "城市异常"),

    /**
     * 县区异常
     */
    COUNTY_ERROR(3073, "县区异常"),

    /**
     * 站内信模板未配置
     */
    IN_MAIL_NULL(3075, "站内信模板未配置"),

    /**
     * 开奖程序出错啦
     */
    LOTTERY_ERROR(3088, "开奖程序出错啦"),

    /**
     * 请先配置支付信息
     */
    PAY_CONFIG_ERROR(3090, "请先配置支付信息"),

    /**
     * 支付接口超时
     */
    CREATE_PAY_ERROR(3092, "支付接口超时"),

    /**
     * 未知支付方式(微信)
     */
    UNKNOWN_PAY_TYPE(3093, "未知支付方式"),

    /**
     * 订单查询失败
     */
    ORDER_QUERY_ERROR(3094, "订单查询失败"),

    /**
     * 订单关闭失败
     */
    ORDER_CLOSE(3095, "订单关闭失败"),

    /**
     * 支付响应错误
     */
    PAY_ERROR(3111, "支付响应错误"),

    /**
     * 支付下单失败
     */
    PAY_ORDER_ERROR(3112, "支付下单失败"),

    /**
     * 退款申请失败
     */
    REFUND_APPLY(3112, "退款申请失败"),

    /**
     * 退款订单查询失败
     */
    REFUND_QUERY(3113, "退款订单查询失败"),

    /**
     * 支付异步通知解析失败
     */
    NOTIFY_PAY_PARSE(3114, "支付异步通知解析失败"),

    /**
     * 退款异步通知解析失败
     */
    NOTIFY_REFUND_PARSE(3115, "退款异步通知解析失败"),

    /**
     * 接口不支持该方法调用
     */
    NOT_SUPPORTED(3116, "接口不支持该方法调用"),

    /**
     * 微信网页授权异常
     */
    REQUEST_ID_EXPIRE(3117, "requestId已过期"),

    /**
     * 收货地址太多啦
     */
    ADDRESS_MAX(3118, "收货地址太多啦~"),

    /**
     * 版本号格式错误
     */
    VERSION_ERROR(3120, "版本号格式错误"),

    /**
     * 使用中的版本无法删除
     */
    CURRENT_VERSION_DELETE(3121, "使用中的版本无法删除"),

    /**
     * 枚举格式化失败
     */
    ENUMS_FORMAT(3123, "枚举格式化失败"),

    /**
     * 异步通知验签失败
     */
    NOTIFY_SIGN_ERROR(3114, "异步通知验签失败"),

    /**
     * 非int或long格式无法转换
     */
    CONVERT_ERROR(3128, "非int或long格式无法转换"),

    /**
     * 商户账户名被占用
     */
    MERCHANT_REDO(3129, "商户账户名被占用"),

    /**
     * 商户手机号被占用
     */
    MERCHANT_MOBILE_REDO(3130, "商户手机号被占用"),

    /**
     * 商户角色未配置
     */
    MERCHANT_ROLE_NULL(3132, "商户角色未配置"),

    /**
     * 民宿名称被占用
     */
    HOMESTAY_TITLE_REDO(3134, "民宿名称被占用"),

    /**
     * 时间跨度不能超过{}天
     */
    MAX_DAY(3136, "时间跨度不能超过%s天"),

    /**
     * 该景区已下架
     */
    SCENIC_DOWN(3137, "该景区已下架"),

    /**
     * 该门票已下架
     */
    TICKET_DOWN(3138, "该门票已下架"),

    /**
     * 优惠券抢完啦
     */
    COUPON_EMPTY(3139, "优惠券抢完啦~"),

    /**
     * 优惠券不在领取时间
     */
    COUPON_INVALID_TIME(3140, "优惠券不在领取时间内"),

    /**
     * 优惠券领取上限啦
     */
    COUPON_MAX(3141, "优惠券领取上限啦~"),

    /**
     * 优惠券领取方式不匹配
     */
    COUPON_MODE_ERROR(3142, "优惠券领取方式不匹配"),

    /**
     * 优惠券不存在
     */
    COUPON_NOT_FOUND(3143, "优惠券不存在"),

    /**
     * 优惠券不合法
     */
    COUPON_ILLEGAL(3144, "优惠券不合法"),

    /**
     * 商品不支持该优惠券
     */
    COUPON_MATCH(3145, "商品不支持该优惠券"),

    /**
     * 优惠券不在有效期
     */
    COUPON_USE_ERROR(3146, "优惠券不在有效期"),

    /**
     * 优惠券不满足使用条件
     */
    COUPON_USE_THRESHOLD(3147, "不满足优惠券使用条件"),

    /**
     * 购物车数量不能超过%s个
     */
    SHOPPING_CART_MAX(3148, "购物车数量不能超过%s个"),

    /**
     * 该商品已下架
     */
    ITEM_DOWN(3149, "该商品已下架"),

    /**
     * 存在已下架的商品
     */
    SKU_DOWN(3150, "存在已下架的商品"),

    /**
     * 商品规格不匹配
     */
    ITEM_SKU_MATCH(3151, "商品规格不匹配"),

    /**
     * 该商品库存不足
     */
    SKU_STOCK(3152, "该商品库存不足"),

    /**
     * 超出商品限购数量
     */
    ITEM_QUOTA(3153, "超出商品限购数量"),

    /**
     * 购物车商品信息不存在
     */
    CART_ITEM_EMPTY(3154, "购物车商品信息不存在"),

    /**
     * 门票库存不足
     */
    TICKET_STOCK(3155, "门票库存不足"),

    /**
     * 请填写游客信息
     */
    TICKET_VISITOR(3156, "请填写游客信息"),

    /**
     * 门票单次购买上限为%s张
     */
    TICKET_QUOTA(3156, "单次购买上限为%s张"),

    /**
     * 订单类型不匹配
     */
    ORDER_TYPE_MATCH(3157, "订单类型不匹配"),

    /**
     * 订单信息不存在
     */
    ORDER_NOT_FOUND(3158, "订单信息不存在"),

    /**
     * 订单状态已变更
     */
    ORDER_PAID(3159, "订单状态已变更"),

    /**
     * 订单已支付,无法取消
     */
    ORDER_PAID_CANCEL(3160, "订单已支付,无法取消"),

    /**
     * 该商品不支持退款
     */
    REFUND_NOT_SUPPORTED(3160, "该商品不支持退款"),

    /**
     * 该订单状态不支持退款
     */
    STATE_NOT_REFUND(3161, "该订单状态不支持退款"),

    /**
     * 订单退款状态不合法
     */
    REFUND_STATE_INVALID(3162, "订单退款状态不合法"),

    /**
     * 退款人数和数量不一致
     */
    REFUND_VISITOR(3163, "退款人数和数量不一致"),

    /**
     * 退款申请已审核
     */
    REFUND_AUDITED(3165, "退款申请已审核"),

    /**
     * 订单不在退款中
     */
    NO_REFUND_STATE(3166, "订单不在退款中"),

    /**
     * 退款记录未查询到
     */
    REFUND_NOT_FOUND(3167, "退款记录未查询到"),

    /**
     * 累计退款金额大于实付金额
     */
    TOTAL_REFUND_MAX(3168, "累计退款金额大于实付金额"),

    /**
     * 订单联系人状态不符合退款要求
     */
    VISITOR_STATE_ERROR(3169, "订单联系人状态不符合退款要求"),

    /**
     * 订单状态不匹配
     */
    ORDER_STATE_MATCH(3170, "订单状态不匹配"),

    /**
     * 该房型已下架
     */
    HOMESTAY_ROOM_NULL(3173, "该房型已下架"),

    /**
     * 该时间段不合法
     */
    HOMESTAY_CONFIG_NULL(3175, "该时间段不合法"),

    /**
     * 房间已预订满
     */
    HOMESTAY_STOCK(3176, "房间已预订满"),

    /**
     * 该餐饮券已下架
     */
    VOUCHER_DOWN(3178, "该餐饮券已下架"),

    /**
     * 餐饮券库存不足
     */
    VOUCHER_STOCK(3179, "餐饮券库存不足"),

    /**
     * 餐饮单次购买上限为%s张
     */
    VOUCHER_QUOTA(3180, "单次购买上限为%s张"),

    /**
     * 线路名称重复
     */
    LINE_TITLE_REDO(3181, "线路名称重复"),

    /**
     * 该线路商品已下架
     */
    LINE_DOWN(3182, "该线路商品已下架"),

    /**
     * 线路所属旅行社下架啦
     */
    TRAVEL_AGENCY_DOWN(3182, "线路所属旅行社下架啦"),

    /**
     * 线路库存不足
     */
    LINE_STOCK(3185, "线路库存不足"),

    /**
     * 商品订单未查询到
     */
    ITEM_ORDER_NULL(3185, "商品订单未查询到"),

    /**
     * 订单信息不匹配
     */
    ORDER_NOT_MATCH(3186, "订单信息不匹配"),

    /**
     * 该商品已申请退款
     */
    ORDER_REFUND_APPLY(3187, "该商品已申请退款"),

    /**
     * 商品退款数量不匹配
     */
    REFUND_MUM_MATCH(3189, "商品退款数量不匹配"),

    /**
     * 状态机[%s]未注册
     */
    STATE_MACHINE_REGISTER(3200, "状态机[%s]未注册"),

    /**
     * 该景区尚未提交审核
     */
    SCENIC_NOT_UP(3201, "该景区尚未提交审核"),

    /**
     * 景区不存在或已删除
     */
    SCENIC_DELETE(3202, "景区不存在或已删除"),

    /**
     * 门票名称重复
     */
    SCENIC_TICKET_REDO(3203, "门票名称重复"),

    /**
     * 景区名称重复
     */
    SCENIC_REDO(3204, "景区名称重复"),

    /**
     * 该景区尚未提交审核
     */
    SCENIC_TICKET_NOT_UP(3205, "该门票尚未提交审核"),

    /**
     * 该民宿尚未提交审核
     */
    HOMESTAY_NOT_UP(3206, "该民宿尚未提交审核"),

    /**
     * 门票订单未查询到
     */
    TICKET_ORDER_NULL(3207, "门票订单未查询到"),

    /**
     * 商品类型不匹配
     */
    PRODUCT_TYPE_MATCH(3210, "商品类型不匹配"),

    /**
     * 公告信息未查询到
     */
    NOTICE_NOT_FOUND(3211, "公告信息未查询到"),

    /**
     * 公告信息未查询到
     */
    NOTICE_NOT_NULL(3212, "公告信息下架啦~"),

    /**
     * 商家信息不存在
     */
    RESTAURANT_NOT_FOUND(3213, "商家信息不存在"),

    /**
     * 退款方式不支持
     */
    REFUND_TYPE_NOT_MATCH(3215, "退款方式不支持"),

    /**
     * 商户账号或密码错误
     */
    MERCHANT_NOT_FOUND(3216, "商户账号或密码错误"),

    /**
     * 商户账号锁定,请联系管理员解锁
     */
    MERCHANT_LOCKED(3218, "商户账号锁定,请联系管理员解锁"),

    /**
     * 商户账号或密码错误
     */
    MERCHANT_PWD_ERROR(3220, "商户账号或密码错误"),

    /**
     * 餐饮券名称重复
     */
    VOUCHER_TITLE_REDO(3230, "餐饮券名称重复"),

    /**
     * 商家名称重复
     */
    RESTAURANT_TITLE_REDO(3233, "商家名称重复"),

    /**
     * 店铺名称重复
     */
    SHOP_TITLE_REDO(3233, "店铺名称重复"),

    /**
     * 商品名称重复
     */
    ITEM_TITLE_REDO(3237, "商品名称重复"),

    /**
     * 房型名称重复
     */
    ROOM_TITLE_REDO(3240, "房型名称重复"),

    /**
     * 优惠券可领取数量上限啦
     */
    COUPON_GET_MAX(3244, "优惠券可领取数量上限啦~"),

    /**
     * 活动名称重复啦~
     */
    ACTIVITY_TITLE_REDO(3250, "活动名称重复啦~"),

    /**
     * 活动名称或ID不能为空
     */
    ACTIVITY_TITLE_NULL(3251, "活动名称或ID不能为空"),

    /**
     * 活动提前结束啦~
     */
    ACTIVITY_DELETE(3254, "活动提前结束啦~"),

    /**
     * 开启定位后方可按距离排序
     */
    POSITION_NO(3258, "开启定位后方可按距离排序"),

    /**
     * 该民宿已下架
     */
    HOMESTAY_NULL(3263, "该民宿已下架"),

    /**
     * 该民宿已下架
     */
    HOMESTAY_DOWN(3266, "该民宿已下架"),

    /**
     * 该店铺已下架
     */
    SHOP_DOWN(3270, "该店铺已下架"),

    /**
     * 存在下架的店铺
     */
    ANY_SHOP_DOWN(3271, "存在下架的店铺"),

    /**
     * 该商家已下架
     */
    RESTAURANT_DOWN(3275, "该商家已下架"),
    
    /**
     * 旅行社名称重复
     */
    TRAVEL_AGENCY_REDO(3300, "旅行社名称重复"),
    
    /**
     * 旅行社信息不存在
     */
    TRAVEL_AGENCY_NOT_FOUND(3302, "旅行社信息不存在"),
    
    /**
     * 规格值重复啦
     */
    SKU_TITLE_REDO(3305, "规格值重复啦"),
    
    /**
     * 活动名称重复啦~
     */
    LOTTERY_REDO(3401, "活动名称重复啦~"),
    
    /**
     * 中奖比例总和应为100%
     */
    LOTTERY_RATIO(3403, "中奖比例总和应为100%"),
    
    /**
     * 抽奖配置位置错误
     */
    LOTTERY_POSITION(3405, "抽奖配置位置错误"),

    /**
     * 抽奖只能在未开始时才能编辑
     */
    LOTTERY_NOT_INIT(3408, "抽奖只能在未开始时才能编辑"),

    /**
     * 抽奖活动结束啦~
     */
    LOTTERY_NULL(3410, "抽奖活动结束啦~"),

    /**
     * 抽奖活动已删除
     */
    LOTTERY_DELETE(3411, "抽奖活动已删除"),

    /**
     * 抽奖活动过期啦
     */
    LOTTERY_EXPIRE(3412, "抽奖活动过期啦~"),

    /**
     * 抽奖太火爆啦~, 请稍后再试
     */
    LOTTERY_EXECUTE(3413, "抽奖太火爆啦~, 请稍后再试"),

    /**
     * 抽奖活动不在有效期
     */
    LOTTERY_STATE(3414, "抽奖活动不在有效期"),

    /**
     * 抽奖次数用完啦~
     */
    LOTTERY_TIME_EMPTY(3416, "抽奖次数用完啦~"),

    /**
     * 该奖品类型不支持
     */
    LOTTERY_PRIZE_ERROR(3418, "该奖品类型不支持"),

    /**
     * 商品太火爆啦,请稍后再试
     */
    ORDER_ERROR(3420, "商品太火爆啦,请稍后再试"),

    /**
     * 核销信息已变更, 请重新扫码
     */
    VERIFY_EXPIRE_ERROR(3425, "核销信息已变更, 请重新扫码"),

    /**
     * 用户[%s]状态异常,无法核销
     */
    VISITOR_VERIFY_ERROR(3426, "用户[%s]无法核销"),

    /**
     * 订单退款处理中,无法核销
     */
    ORDER_REFUND_PROCESS(3428, "订单退款处理中,无法核销"),

    /**
     * 无法核销其他店铺的订单
     */
    ILLEGAL_VERIFY(3429, "无法核销其他店铺的订单"),

    /**
     * 核销码错误, 请重新扫描
     */
    VERIFY_NO_ERROR(3430, "核销码错误, 请重新扫描"),

    /**
     * 核销类型匹配
     */
    VERIFY_TYPE_ERROR(3432, "核销类型匹配"),

    /**
     * 订单不支持扫码核销
     */
    VERIFY_ORDER_ERROR(3433, "订单不支持扫码核销"),

    /**
     * 核销状态不存在
     */
    VERIFY_STATE_ERROR(3435, "核销状态不存在"),

    /**
     * 红包金额太小啦~
     */
    RED_PACKET_ERROR(3600, "红包金额太小啦~"),

    /**
     * 订单未支付,不支持线下退款
     */
    ORDER_UN_PAY(3603, "订单未支付,不支持线下退款"),

    /**
     * 订单已关闭,不支持线下退款
     */
    ORDER_CLOSE_REFUND(3605, "订单已关闭,不支持线下退款"),

    /**
     * 退款用户与订单不匹配
     */
    MEMBER_REFUND_MATCH(3608, "退款用户与订单不匹配"),

    /**
     * 游客[%s]已退款,请勿重复退款
     */
    MEMBER_HAS_REFUND(3612, "游客[%s]已退款,请勿重复退款"),

    /**
     * 存在退款中的游客,不支持线下退款
     */
    MEMBER_HAS_REFUNDING(3615, "存在退款中的游客,不支持线下退款"),

    /**
     * 存在退款中的游客,不支持线下退款
     */
    MEMBER_REFUNDING(3618, "存在退款中的游客,请稍后再试"),

    /**
     * 该账号已禁用,请联系客服解封
     */
    MEMBER_LOGIN_FORBID(3636,"账号已禁用,请联系客服解封"),

    /**
     * 标签id生成错误
     */
    ITEM_TAG_NULL(3646,"标签id生成错误"),

    /**
     * 该快递模板无权限操作
     */
    EXPRESS_NULL(3640,"该快递模板无权限操作"),

    /**
     * 商品不属于同一个店铺
     */
    ITEM_NOT_STORE(3644,"商品不属于同一个店铺"),

    /**
     * 存在已下架的商品
     */
    ITEM_MAY_DOWN(3648,"存在已下架的商品"),

    /**
     * 该地区暂不支持配送
     */
    EXPRESS_NOT_SUPPORT(3653,"该地区暂不支持配送"),

    /**
     * 商品参数非法
     */
    SKU_ITEM_MATCH(3658,"商品参数非法"),

    /**
     * 请选择商品
     */
    ORDER_ITEM_NULL(3662,"请选择商品"),

    /**
     * 该模板已关联商品, 无法修改计费方式
     */
    EXPRESS_CHARGE_MODE(3662,"物流模板已关联商品, 无法修改计费方式"),

    /**
     * 该模板已关联商品, 无法删除模板
     */
    EXPRESS_NOT_DELETE(3665,"物流模板已关联商品, 无法删除模板"),

    /**
     * 物流模板不存在或已删除
     */
    EXPRESS_NOT_FOUND(3668,"物流模板不存在或已删除"),

    /**
     * 物流模板为计重方式,请填写商品重量
     */
    EXPRESS_WEIGHT(3670,"物流模板为计重方式,请填写商品重量"),

    /**
     * 订单信息未查询到
     */
    PRODUCT_SNAPSHOT_NULL(3673,"订单信息未查询到"),

    /**
     * 订单已完成,无法评价
     */
    ORDER_COMPLETE(3675,"订单已完成,无法评价"),

    /**
     * 评论信息未查询到
     */
    EVALUATION_NULL(3677,"评论信息未查询到"),

    /**
     * 微信公众号尚未配置
     */
    MP_NOT_CONFIG(4000, "微信公众号尚未配置"),

    /**
     * 微信网页授权异常
     */
    MP_JS_AUTH(4001, "微信网页授权异常"),

    /**
     * 钉钉客户端初始化异常
     */
    DING_TALK_INIT(5000, "钉钉客户端初始化异常"),




    ;

    /**
     * 错误代码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String msg;

}
