package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统错误信息枚举 包含系统所以的异常信息
 * 404 500等标准错误码返回前台时,可做特殊处理(多个业务共用一个错误码)
 * 1000+ 数据异常
 * 2000+ 业务异常
 *
 * @author 二哥很猛
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {

    SUCCESS(200, "请求成功"),

    /**
     * 用户登陆已过期
     */
    LOGIN_TIMEOUT(8848, "登陆过期,请重新登陆"),

    /**
     * 表示用户没有注册,需要进行手机号授权注册并登录
     */
    MEMBER_REGISTER(8849, "请先授权登录"),

    /**
     * Refresh-Token已过期
     */
    REFRESH_TOKEN_EXPIRE(8850, "Refresh-Token已过期"),

    /**
     * 点击太快啦~~
     */
    SUBMIT_FREQUENTLY(400, "操作太快啦~~"),

    /**
     * 亲~ 暂时没有访问权限哦
     */
    ACCESS_DENIED(403, "亲~ 暂时没有访问权限哦"),

    /**
     * 木有找到请求地址
     */
    PAGE_NOT_FOUND(404, "木有找到请求地址"),

    /**
     * 暂无访问权限 (黑名单拦截)
     */
    SYSTEM_AUTH(405, "暂无访问权限"),

    /**
     * 系统繁忙,请稍后再试
     */
    SYSTEM_ERROR(500, "系统繁忙,请稍后再试"),

    /**
     * 线程中断异常
     */
    THREAD_INTERRUPTED(501, "线程中断异常"),

    /**
     * 应用上下文尚未完全启动
     */
    SPRING_ON_LOADING(505, "Spring上下文尚未完全启动"),

    /**
     * 数据转换异常
     */
    JSON_FORMAT_ERROR(1000, "数据转换异常"),

    /**
     * pbe加密失败
     */
    ENCRYPT_ERROR(1005, "数据加密失败"),

    /**
     * 地址不支持%s请求
     */
    METHOD_NOT_SUPPORTED(1010, "地址不支持%s请求"),

    /**
     * 字符集转换错误
     */
    CHARSET_CONVERT_ERROR(2267, "字符集转换错误"),

    /**
     * 数据解密失败
     */
    DECRYPT_ERROR(1003, "数据解密失败"),

    /**
     * 数据加解密失败
     */
    ENCRYPT_DECRYPT_ERROR(1004, "数据加解密失败"),

    /**
     * 对象转换异常
     */
    BEAN_COPY_ERROR(1005, "对象转换异常"),

    /**
     * 数据加密异常,请联系管理人员
     */
    SHA_256_ERROR(1006, "数据加密异常"),

    /**
     * 验签异常
     */
    SIGN_NULL_ERROR(1007, "验签异常"),

    /**
     * 验签失败
     */
    SIGN_ERROR(1008, "验签失败"),

    /**
     * 未指定方法实现
     */
    NOT_IMPLEMENT(1009, "未指定方法实现"),

    /**
     * 参数转换异常
     */
    PARAMETER_CASE_ERROR(1010, "参数转换异常"),

    /**
     * 未知主机名
     */
    UN_KNOW_HOSTNAME(1011, "未知主机名"),

    /**
     * 未知主机地址
     */
    UN_KNOW_HOST_ADDRESS(1012, "未知主机地址"),

    /**
     * 请求接口非法
     */
    REQUEST_INTERFACE_ERROR(1013, "请求接口非法"),

    /**
     * 请求参数非法
     */
    REQUEST_PARAM_ILLEGAL(1014, "请求参数非法"),

    /**
     * 请求解析异常
     */
    REQUEST_RESOLVE_ERROR(1015, "请求解析异常"),

    /**
     * 签名信息为空
     */
    SIGNATURE_ERROR(1018, "签名信息为空"),

    /**
     * 时间戳为空
     */
    SIGNATURE_TIMESTAMP_NULL(1020, "数字签名为空"),

    /**
     * 签名信息已过期
     */
    SIGNATURE_EXPIRE(1023, "签名信息已过期"),

    /**
     * 签名已过期,请重新申请签名
     */
    SIGNATURE_TIMESTAMP_ERROR(1025, "签名已过期,请重新申请签名"),

    /**
     * 签名校验异常
     */
    SIGNATURE_VERIFY_ERROR(1031, "签名校验失败"),

    /**
     * 读取请求参数异常
     */
    READ_PARAM_ERROR(1033, "读取请求参数异常"),

    /**
     * 非法操作 操作了不属于自己的数据
     */
    ILLEGAL_OPERATION(1035, "非法操作"),

    /**
     * 非管理员,无权限进行授权操作
     */
    ADMIN_AUTH(1043, "非管理员,无权限进行授权操作"),

    /**
     * 转换器不支持非枚举类
     */
    ENUM_SUPPORTED(1046, "转换器不支持非枚举类"),

    /**
     * 定时任务尚未激活
     */
    TASK_CONFIG_NULL(1048, "定时任务尚未激活"),

    /**
     * 定时任务配置异常
     */
    TASK_CONFIG_ERROR(1051, "定时任务配置异常"),

    /**
     * 任务未查询到
     */
    TASK_NULL_ERROR(1057, "任务未查询到"),

    /**
     * 数据权限不匹配
     */
    DATA_TYPE_ERROR(1059, "数据权限不匹配"),

    /**
     * 缓存value不能为空
     */
    CACHE_VALUE_NULL(1103, "缓存value不能为空"),

    /**
     * 屏幕锁定中
     */
    LOCK_SCREEN(1024, "屏幕锁定中"),

    /**
     * 该字段类型不支持脱敏
     */
    FIELD_NOT_SUPPORTED(2243, "该字段类型不支持脱敏"),

    /**
     * 参数解析异常
     */
    PARAM_VERIFY_ERROR(2003, "%s"),

    /**
     * 参数解析异常
     */
    PARAM_NULL_ERROR(2006, "%s参数不能为空"),

    /**
     * 日期格式转换异常
     */
    DATE_CASE_ERROR(2027, "日期格式转换异常"),

    /**
     * 日期格式必须为yyyy-MM-dd
     */
    DATE_ERROR(2030, "日期格式必须为yyyy-MM-dd"),

    /**
     * 模板解析失败
     */
    TEMPLATE_RENDER_ERROR(2033, "模板解析失败"),

    /**
     * 手机号码被占用
     */
    MOBILE_REDO(2036, "手机号码被占用"),

    /**
     * 系统默认角色,禁止删除
     */
    ROLE_FORBID_DELETE(2039, "系统默认角色,禁止删除"),

    /**
     * 角色名称重复
     */
    ROLE_NAME_REDO(2042, "角色名称重复"),

    /**
     * 角色信息不存在
     */
    ROLE_NULL(2045, "角色信息不存在"),

    /**
     * ip格式错误
     */
    IP_ILLEGAL(2048, "IP格式错误"),

    /**
     * 开始IP必须小于截止IP
     */
    IP_RANGE_ILLEGAL(2051, "开始IP必须小于截止IP"),

    /**
     * 系统配置信息未查询到
     */
    CONFIG_NOT_FOUND_ERROR(2054, "系统配置信息未查询到"),

    /**
     * 系统配置信息更新失败
     */
    UPDATE_CONFIG_ERROR(2057, "系统配置信息更新失败"),

    /**
     * 菜单信息更新失败
     */
    UPDATE_MENU_ERROR(2060, "菜单信息更新失败"),

    /**
     * 父菜单不存在
     */
    PID_MENU_NULL(2063, "父菜单不存在"),

    /**
     * 菜单名称重复啦
     */
    MENU_TITLE_REDO(2066, "菜单名称重复啦"),

    /**
     * 菜单显示状态与父菜单不匹配
     */
    PID_MENU_STATE(2069, "菜单显示状态与父菜单不匹配"),

    /**
     * 验证码输入错误
     */
    IMAGE_CODE_ERROR(2072, "验证码输入错误"),

    /**
     * 账号或密码输入错误
     */
    ACCOUNT_PASSWORD_ERROR(2114, "账号或密码输入错误"),

    /**
     * 密码输入错误
     */
    PASSWORD_ERROR(2115, "密码输入错误"),

    /**
     * 用户信息不存在
     */
    USER_NOT_FOUND(2078, "用户信息不存在"),

    /**
     * 用户类型不匹配
     */
    USER_TYPE_NULL(2079, "用户类型不匹配"),

    /**
     * 错误次数太多,请10分钟后再试
     */
    USER_ERROR_LOCK(2080, "错误次数太多,请10分钟后再试"),

    /**
     * 用户已锁定,请联系管理人员
     */
    USER_LOCKED_ERROR(2081, "用户已锁定,请联系管理人员"),

    /**
     * 系统参数已锁定
     */
    CONFIG_LOCK_ERROR(2084, "系统参数已锁定"),

    /**
     * 旧密码输入错误
     */
    USER_PASSWORD_ERROR(2114, "旧密码输入错误"),

    /**
     * 上传文件过大
     */
    UPLOAD_TOO_BIG(2090, "单文件最大:%sM"),

    /**
     * 文件保存失败
     */
    FILE_SAVE_ERROR(2093, "文件保存失败"),

    /**
     * 创建文件失败
     */
    FILE_CREATE_ERROR(2096, "创建文件失败"),

    /**
     * 显示值重复啦~
     */
    DICT_SHOW_REPEAT_ERROR(2097, "显示值重复啦~"),

    /**
     * 隐藏值重复啦~
     */
    DICT_HIDDEN_REPEAT_ERROR(2098, "隐藏值重复啦~"),

    /**
     * 系统字典不支持删除
     */
    DICT_LOCKED_ERROR(2099, "系统字典不支持删除"),

    /**
     * 数据字典名称重复啦~
     */
    DICT_REPEAT_ERROR(2100, "字典名称重复啦~"),

    /**
     * 字典编码重复啦~
     */
    DICT_NID_REPEAT_ERROR(2101, "字典编码重复啦~"),

    /**
     * 同级菜单最多90个
     */
    MENU_MAX_ERROR(2102, "同级菜单最多90个"),

    /**
     * 用户已在其他设备上登陆
     */
    MULTIPLE_CLIENT_LOGIN(2105, "用户已在其他设备上登陆"),

    /**
     * 用户不存在
     */
    MEMBER_NOT_FOUND(2108, "用户不存在"),

    /**
     * 该手机号尚未注册
     */
    MEMBER_NOT_REGISTER(2111, "该手机号尚未注册"),

    /**
     * 账号或密码错误
     */
    MEMBER_PASSWORD_ERROR(2114, "账号或密码错误"),

    /**
     * 验证码已过期
     */
    LOGIN_SMS_CODE_EXPIRE(2117, "验证码已过期"),

    /**
     * 短信验证码错误
     */
    LOGIN_SMS_CODE_ERROR(2120, "验证码输入错误"),

    /**
     * 手机号未注册
     */
    MOBILE_NOT_REGISTER(2123, "手机号未注册"),

    /**
     * 短信发送频率太快
     */
    SMS_FREQUENCY_FAST(2126, "短信发送频率太快"),

    /**
     * 单位小时内短信发送上限
     */
    SMS_HOUR_LIMIT(2129, "单位小时内短信发送上限"),

    /**
     * 当天内短信发送上限(统一类型短信)
     */
    SMS_DAY_LIMIT(2132, "当天内短信发送上限"),

    /**
     * 当天内短信发送上限(统一手机号短信)
     */
    MOBILE_DAY_LIMIT(2135, "当天内短信发送上限"),

    /**
     * 版本号重复
     */
    VERSION_REDO(2138, "版本号重复"),

    /**
     * 该手机号已注册
     */
    MOBILE_REGISTER_REDO(2141, "该手机号已注册"),

    /**
     * cron表达式配置错误
     */
    CRON_CONFIG_ERROR(2144, "cron表达式配置错误"),

    /**
     * 按钮菜单无法添加子菜单
     */
    SUB_MENU_ERROR(2147, "按钮菜单无法添加子菜单"),

    /**
     * 内容不能为空
     */
    TEXT_CONTENT_EMPTY(2150, "内容不能为空"),

    /**
     * 新设备登陆校验
     */
    NEW_DEVICE_LOGIN(2153, "新设备登陆校验"),

    /**
     * 账号在其他设备上登陆
     */
    KICK_OFF_LINE(2156, "账号在其他设备上登陆"),

    /**
     * 邮件服务可能未配置
     */
    MAIL_NOT_CONFIG(2159, "邮件服务可能未配置"),

    /**
     * 部门层级已上限
     */
    DEPARTMENT_DEPTH_ERROR(2162, "部门层级已上限"),

    /**
     * 部门名称重复啦
     */
    DEPARTMENT_TITLE_REPEAT(2163, "部门名称重复啦"),

    /**
     * 邮件模板未配置
     */
    EMAIL_TEMPLATE_NULL(2165, "邮件模板未配置"),

    /**
     * 请勿重复绑定邮箱
     */
    EMAIL_REDO_BIND(2168, "请勿重复绑定邮箱"),

    /**
     * 请先获取邮箱验证码
     */
    EMAIL_ADDRESS_ERROR(2171, "请先获取邮箱验证码"),

    /**
     * 邮箱验证码输入错误
     */
    EMAIL_CODE_ERROR(2174, "邮箱验证码输入错误"),

    /**
     * 邮箱已被占用
     */
    EMAIL_OCCUPY_ERROR(2177, "邮箱已被占用"),

    /**
     * 请先绑定手机号
     */
    MOBILE_NOT_BIND(2180, "请先绑定手机号"),

    /**
     * 请勿重复签到
     */
    SIGN_IN_REDO(2183, "请勿重复签到"),

    /**
     * 数据格式异常
     */
    DATA_ERROR(2186, "数据格式异常"),

    /**
     * 用户地址未查询到
     */
    MEMBER_ADDRESS_NULL(2252, "用户地址未查询到"),

    /**
     * 省份异常
     */
    PROVINCE_ERROR(2192, "省份异常"),

    /**
     * 城市异常
     */
    CITY_ERROR(2195, "城市异常"),

    /**
     * 县区异常
     */
    COUNTY_ERROR(2198, "县区异常"),

    /**
     * 站内信模板未配置
     */
    IN_MAIL_NULL(2201, "站内信模板未配置"),

    /**
     * 开奖程序出错啦
     */
    LOTTERY_ERROR(2204, "开奖程序出错啦"),

    /**
     * 请先配置支付信息
     */
    PAY_CONFIG_ERROR(2207, "请先配置支付信息"),

    /**
     * 支付接口超时
     */
    CREATE_PAY_ERROR(2222, "支付接口超时"),

    /**
     * 未知支付方式(微信)
     */
    UNKNOWN_PAY_TYPE(2213, "未知支付方式"),

    /**
     * 订单查询失败
     */
    ORDER_QUERY_ERROR(2216, "订单查询失败"),

    /**
     * 订单关闭失败
     */
    ORDER_CLOSE(2219, "订单关闭失败"),

    /**
     * 支付响应错误
     */
    PAY_ERROR(2222, "支付响应错误"),

    /**
     * 支付订单创建失败
     */
    PAY_ORDER_ERROR(2624, "支付订单创建失败"),

    /**
     * 退款请求超时,请稍后再试
     */
    WECHAT_REFUND_APPLY(2228, "退款请求超时,请稍后再试"),

    /**
     * 退款请求超时,请稍后再试
     */
    ALI_REFUND_APPLY(2231, "退款请求超时,请稍后再试"),

    /**
     * 退款订单查询失败
     */
    REFUND_QUERY(2234, "退款订单查询失败"),

    /**
     * 支付异步通知解析失败
     */
    NOTIFY_PAY_PARSE(2237, "支付异步通知解析失败"),

    /**
     * 退款异步通知解析失败
     */
    NOTIFY_REFUND_PARSE(2240, "退款异步通知解析失败"),

    /**
     * 接口不支持该方法调用
     */
    NOT_SUPPORTED(2243, "接口不支持该方法调用"),

    /**
     * 短信验证码过期啦~
     */
    REQUEST_ID_EXPIRE(2246, "短信验证码过期啦~"),

    /**
     * 收货地址太多啦
     */
    ADDRESS_MAX(2249, "收货地址太多啦~"),

    /**
     * 收货地址不见啦~
     */
    ADDRESS_NULL(2252, "收货地址不见啦~"),

    /**
     * 版本号格式错误
     */
    VERSION_ERROR(2255, "版本号格式错误"),

    /**
     * 使用中的版本无法删除
     */
    CURRENT_VERSION_DELETE(2258, "使用中的版本无法删除"),

    /**
     * 枚举格式化失败
     */
    ENUMS_FORMAT(2261, "枚举格式化失败"),

    /**
     * 异步通知验签失败
     */
    NOTIFY_SIGN_ERROR(2264, "异步通知验签失败"),

    /**
     * 非int或long格式无法转换
     */
    CONVERT_ERROR(2267, "非int或long格式无法转换"),

    /**
     * 商户名称重复啦~
     */
    MERCHANT_REDO(2270, "商户名称重复啦~"),

    /**
     * 社会统一信用代码重复啦~
     */
    CREDIT_CODE_REDO(2273, "社会统一信用代码重复啦~"),

    /**
     * 商户手机号被占用
     */
    MERCHANT_MOBILE_REDO(2276, "商户手机号被占用"),

    /**
     * 商户角色未配置
     */
    MERCHANT_ROLE_NULL(2279, "商户角色未配置"),

    /**
     * 民宿名称被占用
     */
    HOMESTAY_TITLE_REDO(2282, "民宿名称被占用"),

    /**
     * 时间跨度不能超过{}天
     */
    MAX_DAY(2285, "时间跨度不能超过%s天"),

    /**
     * 该景区下架啦~
     */
    SCENIC_DOWN(2288, "该景区下架啦~"),

    /**
     * 门票商品下架啦~
     */
    TICKET_DOWN(2291, "门票商品下架啦~"),

    /**
     * 请选择要发放优惠券的会员
     */
    COUPON_MEMBER_NULL(2293, "请选择要发放优惠券的会员"),

    /**
     * 优惠券抢完啦
     */
    COUPON_EMPTY(2294, "优惠券抢完啦~"),

    /**
     * 优惠券不在领取时间
     */
    COUPON_INVALID_TIME(2297, "优惠券不在领取时间内"),

    /**
     * 优惠券领取上限啦
     */
    COUPON_MAX(2300, "优惠券领取上限啦~"),

    /**
     * 优惠券领取方式不匹配
     */
    COUPON_MODE_ERROR(2303, "优惠券领取方式不匹配"),

    /**
     * 优惠券不存在
     */
    COUPON_NOT_FOUND(2306, "优惠券不存在"),

    /**
     * 优惠券不合法
     */
    COUPON_ILLEGAL(2309, "优惠券不合法"),

    /**
     * 商品不支持该优惠券
     */
    COUPON_MATCH(2312, "商品不支持该优惠券"),

    /**
     * 优惠券不在有效期
     */
    COUPON_USE_ERROR(2315, "优惠券不在有效期"),

    /**
     * 请勿重复使用优惠券
     */
    COUPON_USED(2316, "请勿重复使用优惠券"),

    /**
     * 优惠券不满足使用条件
     */
    COUPON_USE_THRESHOLD(2318, "不满足优惠券使用条件"),

    /**
     * 购物车数量不能超过%s个
     */
    SHOPPING_CART_MAX(2321, "购物车数量不能超过%s个"),

    /**
     * 该商品已下架
     */
    ITEM_DOWN(2324, "该商品已下架"),

    /**
     * 存在已下架的商品
     */
    SKU_DOWN(2327, "存在已下架的商品"),

    /**
     * 商品规格不匹配
     */
    ITEM_SKU_MATCH(2330, "商品规格不匹配"),

    /**
     * 该商品库存不足
     */
    SKU_STOCK(2333, "该商品库存不足"),

    /**
     * 超出商品限购数量
     */
    ITEM_QUOTA(2336, "超出商品限购数量"),

    /**
     * 商品规格不允许修改
     */
    ITEM_MULTI_SPEC_ERROR(2337, "商品规格不允许修改"),

    /**
     * 购物车商品信息不存在
     */
    CART_ITEM_EMPTY(2339, "购物车商品信息不存在"),

    /**
     * 门票库存不足
     */
    TICKET_STOCK(2342, "门票库存不足"),

    /**
     * 请填写游客信息
     */
    TICKET_VISITOR(2345, "请填写游客信息"),

    /**
     * 请提前%s天购买门票
     */
    TICKET_ADVANCE_DAY(2348, "请提前%s天购买门票"),

    /**
     * 门票单次购买上限为%s张
     */
    TICKET_QUOTA(2351, "单次购买上限为%s张"),

    /**
     * 订单类型不匹配
     */
    ORDER_TYPE_MATCH(2354, "订单类型不匹配"),

    /**
     * 订单信息不存在
     */
    ORDER_NOT_FOUND(2357, "订单信息不存在"),

    /**
     * 订单状态已变更
     */
    ORDER_PAID(2360, "订单状态已变更"),

    /**
     * 商品无需发货
     */
    ITEM_NO_SHIPMENT(2361, "商品无需发货"),

    /**
     * 自提商品无需发货
     */
    ITEM_SELF_PICK(2362, "自提商品无需发货"),

    /**
     * 订单已支付,无法取消
     */
    ORDER_PAID_CANCEL(2363, "订单已支付,无法取消"),

    /**
     * 该商品不支持退款
     */
    REFUND_NOT_SUPPORTED(2366, "该商品不支持退款"),

    /**
     * 该订单状态不支持退款
     */
    STATE_NOT_REFUND(2369, "该订单状态不支持退款"),

    /**
     * 订单退款中,请勿重复申请
     */
    REFUND_STATE_INVALID(2372, "订单退款中,请勿重复申请"),

    /**
     * 退款人数和数量不一致
     */
    REFUND_VISITOR(2375, "退款人数和数量不一致"),

    /**
     * 退款申请已审核
     */
    REFUND_AUDITED(2378, "退款申请已审核"),

    /**
     * 订单不在退款中
     */
    NO_REFUND_STATE(2468, "订单不在退款中"),

    /**
     * 退款记录未查询到
     */
    REFUND_NOT_FOUND(2384, "退款记录未查询到"),

    /**
     * 累计退款金额大于实付金额
     */
    TOTAL_REFUND_MAX(2387, "累计退款金额大于实付金额"),

    /**
     * 请勿重复退款
     */
    ITEM_REFUND(2390, "请勿重复退款"),

    /**
     * 该订单已超过售后时间
     */
    ITEM_REFUND_EXPIRE(2393, "该订单已超过售后时间"),

    /**
     * 累计退款数量大于实付数量
     */
    TOTAL_REFUND_MAX_NUM(2396, "累计退款数量大于实付数量"),

    /**
     * 累计退款金额不能大于实付金额
     */
    REFUND_GT_PAY(2399, "累计退款金额不能大于实付金额"),

    /**
     * 订单状态不符合退款要求
     */
    VISITOR_STATE_ERROR(2402, "订单状态不符合退款要求"),

    /**
     * 订单状态不匹配
     */
    ORDER_STATE_MATCH(2405, "订单状态不匹配"),

    /**
     * 订单状态不匹配,无法核销
     */
    ORDER_NOT_VERIFY(2408, "订单状态不匹配,无法核销"),

    /**
     * 该房型已下架
     */
    HOMESTAY_ROOM_DOWN(2411, "该房型已下架"),

    /**
     * 该房型已删除
     */
    HOMESTAY_ROOM_NULL(2414, "房型信息未查询到"),

    /**
     * 房间库存不足, 请刷新页面
     */
    HOMESTAY_CONFIG_NULL(2417, "房间库存不足,请刷新页面"),

    /**
     * 房间已预订满
     */
    HOMESTAY_STOCK(2420, "房间已预订满"),

    /**
     * 餐饮券未查询到
     */
    VOUCHER_NULL(2423, "餐饮券未查询到"),

    /**
     * 该餐饮券已下架
     */
    VOUCHER_DOWN(2426, "该餐饮券已下架"),

    /**
     * 餐饮券库存不足
     */
    VOUCHER_STOCK(2429, "餐饮券库存不足"),

    /**
     * 餐饮单次购买上限为%s张
     */
    VOUCHER_QUOTA(2432, "单次购买上限为%s张"),

    /**
     * 标签名称重复啦~
     */
    VOUCHER_TAG_REDO(2433, "标签名称重复啦~"),

    /**
     * 线路名称重复
     */
    LINE_TITLE_REDO(2435, "线路名称重复"),

    /**
     * 线路商品下架啦~
     */
    LINE_DOWN(2438, "线路商品下架啦~"),

    /**
     * 线路信息未查询到
     */
    LINE_NULL(2441, "线路信息未查询到"),

    /**
     * 线路库存不足
     */
    LINE_STOCK(2444, "线路库存不足"),

    /**
     * 该日期不可预定
     */
    LINE_NOT_ORDER(2447, "该日期不可预定"),

    /**
     * 请提前%s天预约
     */
    LINE_ADVANCE_DAY(2450, "请提前%s天预约"),

    /**
     * 商品订单未查询到
     */
    ITEM_ORDER_NULL(2453, "商品订单未查询到"),

    /**
     * 订单信息不匹配
     */
    ORDER_NOT_MATCH(2456, "订单信息不匹配"),

    /**
     * 该商品已申请退款
     */
    ORDER_REFUND_APPLY(2459, "该商品已申请退款"),

    /**
     * 线路库存不足
     */
    LINE_STOCK_NULL(2462, "线路库存不足"),

    /**
     * 商品退款数量不匹配
     */
    REFUND_MUM_MATCH(2465, "商品退款数量不匹配"),

    /**
     * 订单状态不匹配,无法退款
     */
    REFUND_STATE(2468, "订单状态不匹配,无法退款"),

    /**
     * 线路商品不存在
     */
    LINE_DELETE(2471, "线路商品不存在"),

    /**
     * 状态机[%s]未注册
     */
    STATE_MACHINE_REGISTER(2474, "状态机[%s]未注册"),

    /**
     * 该景区尚未提交审核
     */
    SCENIC_NOT_UP(2477, "该景区尚未提交审核"),

    /**
     * 景区不存在或已删除
     */
    SCENIC_DELETE(2480, "景区不存在或已删除"),

    /**
     * 门票名称重复
     */
    SCENIC_TICKET_REDO(2483, "门票名称重复"),

    /**
     * 景区名称重复
     */
    SCENIC_REDO(2486, "景区名称重复"),

    /**
     * 该景区尚未提交审核
     */
    SCENIC_TICKET_NOT_UP(2489, "该门票尚未提交审核"),

    /**
     * 该民宿尚未提交审核
     */
    HOMESTAY_NOT_UP(2492, "该民宿尚未提交审核"),

    /**
     * 门票订单未查询到
     */
    TICKET_ORDER_NULL(2495, "门票订单未查询到"),

    /**
     * 免费票无需退款
     */
    TICKET_ORDER_FREE(2498, "免费票无需退款"),

    /**
     * 商品类型不匹配
     */
    PRODUCT_TYPE_MATCH(2501, "商品类型不匹配"),

    /**
     * 公告信息未查询到
     */
    NOTICE_NOT_FOUND(2504, "公告信息未查询到"),

    /**
     * 公告信息未查询到
     */
    NOTICE_NOT_NULL(2507, "公告信息下架啦~"),

    /**
     * 店铺信息未查询到
     */
    RESTAURANT_NULL(2510, "店铺信息未查询到"),

    /**
     * 线路所属旅行社下架啦
     */
    TRAVEL_AGENCY_DOWN(2513, "线路所属旅行社下架啦"),

    /**
     * 退款方式不支持
     */
    REFUND_TYPE_NOT_MATCH(2516, "退款方式不支持"),

    /**
     * 商户账号或密码错误
     */
    MERCHANT_NOT_FOUND(2519, "商户账号或密码错误"),

    /**
     * 商户账号锁定,请联系管理员解锁
     */
    MERCHANT_LOCKED(2522, "商户账号锁定,请联系管理员解锁"),

    /**
     * 商户账号或密码错误
     */
    MERCHANT_PWD_ERROR(2525, "商户账号或密码错误"),

    /**
     * 餐饮券名称重复
     */
    VOUCHER_TITLE_REDO(2528, "餐饮券名称重复"),

    /**
     * 商家名称重复
     */
    RESTAURANT_TITLE_REDO(2531, "商家名称重复"),

    /**
     * 店铺名称重复
     */
    SHOP_TITLE_REDO(2534, "店铺名称重复"),

    /**
     * 商品名称重复
     */
    ITEM_TITLE_REDO(2537, "商品名称重复"),

    /**
     * 房型名称重复
     */
    ROOM_TITLE_REDO(2540, "房型名称重复"),

    /**
     * 优惠券可领取数量上限啦
     */
    COUPON_GET_MAX(2543, "优惠券可领取数量上限啦~"),

    /**
     * 活动名称重复啦~
     */
    ACTIVITY_TITLE_REDO(2546, "活动名称重复啦~"),

    /**
     * 活动下架啦~
     */
    ACTIVITY_NULL(2547, "活动下架啦~"),

    /**
     * 该活动不支持评论
     */
    ACTIVITY_COMMENT_FORBID(2790, "该活动不支持评论"),

    /**
     * 活动名称或ID不能为空
     */
    ACTIVITY_TITLE_NULL(2549, "活动名称或ID不能为空"),

    /**
     * 活动已经结束啦~
     */
    ACTIVITY_DELETE(2552, "活动已经结束啦~"),

    /**
     * 开启定位后方可按距离排序
     */
    POSITION_NO(2555, "请开启定位"),

    /**
     * 最大预定间隔不能超过%d天
     */
    HOMESTAY_SEARCH_MAX(2558, "最大预定间隔不能超过%d天"),

    /**
     * 该民宿已下架
     */
    HOMESTAY_NULL(2561, "民宿信息未查询到"),

    /**
     * 民宿已经下架啦~
     */
    HOMESTAY_DOWN(2564, "民宿已经下架啦~"),

    /**
     * 该店铺已下架
     */
    SHOP_DOWN(2567, "该店铺已下架"),

    /**
     * 店铺信息未查询到
     */
    SHOP_NULL(2570, "店铺信息未查询到"),

    /**
     * 请先补全店铺信息
     */
    SHOP_NOT_PERFECT(2573, "请先补全店铺信息"),

    /**
     * 存在下架的店铺
     */
    ANY_SHOP_DOWN(2576, "存在下架的店铺"),

    /**
     * 该商家已下架
     */
    RESTAURANT_DOWN(2579, "该商家已下架"),

    /**
     * 旅行社名称重复
     */
    TRAVEL_AGENCY_REDO(2582, "旅行社名称重复"),

    /**
     * 旅行社信息不存在
     */
    TRAVEL_AGENCY_NOT_FOUND(2585, "旅行社信息不存在"),

    /**
     * 规格值重复啦
     */
    SKU_TITLE_REDO(2588, "规格值重复啦"),

    /**
     * 活动名称重复啦~
     */
    LOTTERY_REDO(2591, "活动名称重复啦~"),

    /**
     * 中奖比例总和应为100%
     */
    LOTTERY_RATIO(2594, "中奖比例总和应为100%"),

    /**
     * 抽奖配置位置错误
     */
    LOTTERY_POSITION(2597, "抽奖配置位置错误"),

    /**
     * 抽奖只能在未开始时才能编辑
     */
    LOTTERY_NOT_INIT(2600, "抽奖只能在未开始时才能编辑"),

    /**
     * 抽奖活动结束啦~
     */
    LOTTERY_NULL(2603, "抽奖活动结束啦~"),

    /**
     * 抽奖活动已删除
     */
    LOTTERY_DELETE(2606, "抽奖活动已删除"),

    /**
     * 抽奖活动过期啦
     */
    LOTTERY_EXPIRE(2609, "抽奖活动过期啦~"),

    /**
     * 抽奖太火爆啦~, 请稍后再试
     */
    LOTTERY_EXECUTE(2612, "抽奖太火爆啦~, 请稍后再试"),

    /**
     * 抽奖活动不在有效期
     */
    LOTTERY_STATE(2615, "抽奖活动不在有效期"),

    /**
     * 抽奖次数用完啦~
     */
    LOTTERY_TIME_EMPTY(2618, "抽奖次数用完啦~"),

    /**
     * 该奖品类型不支持
     */
    LOTTERY_PRIZE_ERROR(2621, "该奖品类型不支持"),

    /**
     * 必须包含谢谢参与的商品
     */
    LOTTERY_PRIZE_TYPE(2622, "必须包含谢谢参与的商品"),

    /**
     * 商品太火爆啦,请稍后再试
     */
    ORDER_ERROR(2624, "商品太火爆啦,请稍后再试"),

    /**
     * 核销码已过期, 请重新扫码
     */
    VERIFY_EXPIRE_ERROR(2627, "核销码已过期, 请重新扫码"),

    /**
     * 用户[%s]状态异常,无法核销
     */
    VISITOR_VERIFY_ERROR(2630, "用户[%s]无法核销"),

    /**
     * 核销码不属于该商户,无法核销
     */
    VERIFY_ACCESS_DENIED(2633, "核销码不属于该商户,无法核销"),

    /**
     * 订单退款处理中,无法核销
     */
    ORDER_REFUND_PROCESS(2636, "订单退款处理中,无法核销"),

    /**
     * 无法核销其他店铺的订单
     */
    ILLEGAL_VERIFY(2639, "无法核销其他店铺的订单"),

    /**
     * 核销码错误, 请重新扫描
     */
    VERIFY_NO_ERROR(2642, "核销码错误, 请重新扫描"),

    /**
     * 核销类型匹配
     */
    VERIFY_TYPE_ERROR(2645, "核销类型匹配"),

    /**
     * 订单不支持扫码核销
     */
    VERIFY_ORDER_ERROR(2648, "订单不支持扫码核销"),

    /**
     * 核销状态不存在
     */
    VERIFY_STATE_ERROR(2651, "核销状态不存在"),

    /**
     * 红包金额太小啦~
     */
    RED_PACKET_ERROR(2654, "红包金额太小啦~"),

    /**
     * 订单未支付,不支持线下退款
     */
    ORDER_UN_PAY(2657, "订单未支付,不支持线下退款"),

    /**
     * 订单已关闭,不支持线下退款
     */
    ORDER_CLOSE_REFUND(2660, "订单已关闭,不支持线下退款"),

    /**
     * 退款用户与订单不匹配
     */
    MEMBER_REFUND_MATCH(2663, "退款用户与订单不匹配"),

    /**
     * 游客[%s]已退款,请勿重复退款
     */
    MEMBER_HAS_REFUND(2666, "游客[%s]已退款,请勿重复退款"),

    /**
     * 存在退款中的游客,不支持线下退款
     */
    MEMBER_HAS_REFUNDING(2669, "存在退款中的游客,不支持线下退款"),

    /**
     * 存在退款中的商品,不支持线下退款
     */
    ITEM_HAS_REFUNDING(2672, "存在退款中的商品,不支持线下退款"),

    /**
     * 存在退款中的游客,不支持线下退款
     */
    MEMBER_REFUNDING(2675, "存在退款中的游客,请稍后再试"),

    /**
     * 该账号已禁用,详细情况请联系客服
     */
    MEMBER_LOGIN_FORBID(2678, "账号已禁用,详细情况请联系客服"),

    /**
     * 标签id生成错误
     */
    ITEM_TAG_NULL(3026, "标签id生成错误"),

    /**
     * 标签已被使用,无法删除
     */
    TAG_USED(2684, "标签已被使用,无法删除"),

    /**
     * 标签层级太深,不支持创建
     */
    TAG_DEPTH(2687, "标签层级太深,不支持创建"),

    /**
     * 该快递模板无权限操作
     */
    EXPRESS_NULL(2690, "该快递模板无权限操作"),

    /**
     * 商品不属于同一个店铺
     */
    ITEM_NOT_STORE(2693, "商品不属于同一个店铺"),

    /**
     * 存在已下架的商品
     */
    ITEM_MAY_DOWN(2696, "存在已下架的商品"),

    /**
     * 该地区暂不支持配送
     */
    EXPRESS_NOT_SUPPORT(2699, "该地区暂不支持配送"),

    /**
     * 商品参数非法
     */
    SKU_ITEM_MATCH(2702, "商品参数非法"),

    /**
     * 请选择商品
     */
    ORDER_ITEM_NULL(2705, "请选择商品"),

    /**
     * 请选择要发货的商品
     */
    ORDER_ITEM_CHOOSE(2708, "请选择要发货的商品"),

    /**
     * 该模板已关联商品, 无法修改计费方式
     */
    EXPRESS_CHARGE_MODE(2711, "物流模板已关联商品, 无法修改计费方式"),

    /**
     * 该模板已关联商品, 无法删除模板
     */
    EXPRESS_NOT_DELETE(2714, "物流模板已关联商品, 无法删除模板"),

    /**
     * 请选择未退款的商品
     */
    CHOOSE_NO_REFUND(2717, "请选择未退款的商品"),

    /**
     * 请选择未发货的商品
     */
    CHOOSE_NO_DELIVERY(2720, "请选择未发货的商品"),

    /**
     * 请选择可以发快递的商品
     */
    CHOOSE_EXPRESS(2723, "请选择可以发快递的商品"),

    /**
     * 物流模板不存在或已删除
     */
    EXPRESS_NOT_FOUND(2726, "物流模板不存在或已删除"),

    /**
     * 物流模板为计重方式,请填写商品重量
     */
    EXPRESS_WEIGHT(2729, "物流模板为计重方式,请填写商品重量"),

    /**
     * 订单信息未查询到
     */
    PRODUCT_SNAPSHOT_NULL(2732, "订单信息未查询到"),

    /**
     * 物流信息未查询到
     */
    ORDER_EXPRESS_NULL(2733, "物流信息未查询到"),

    /**
     * 订单评价重复啦~
     */
    EVALUATION_REDO(2735, "订单评价重复啦~"),

    /**
     * 订单状态不合法, 无法评价
     */
    ORDER_COMPLETE(2738, "订单状态不合法, 无法评价"),

    /**
     * 评论信息未查询到
     */
    EVALUATION_NULL(2741, "评论信息未查询到"),

    /**
     * 商户用户未查询到
     */
    MERCHANT_USER_NULL(2744, "商户用户未查询到"),

    /**
     * 单位名称重复啦
     */
    AUTH_TITLE_REDO(2747, "单位名称重复啦"),

    /**
     * 快递信息未查询到
     */
    EXPRESS_SELECT_NULL(2750, "快递信息未查询到"),

    /**
     * 区域名称重复
     */
    AREA_TITLE_REDO(2753, "区域名称重复"),

    /**
     * 区域信息未查询到
     */
    AREA_NOT_EXIST(2754, "区域信息未查询到"),

    /**
     * 区域编号重复
     */
    AREA_CODE_REDO(2756, "区域编号重复"),

    /**
     * POI类型重复
     */
    TYPE_CODE_REDO(2759, "POI类型重复"),

    /**
     * POI类型已被使用
     */
    TYPE_POINT_OCCUPY(2762, "POI类型已被使用"),

    /**
     * 点位名称重复
     */
    AREA_POINT_REDO(2765, "点位名称重复"),

    /**
     * POI点位信息不存在
     */
    AREA_POINT_NULL(2768, "POI点位信息不存在"),

    /**
     * 请先绑定点位信息
     */
    BIND_POINT_NULL(2771, "请先绑定点位信息"),

    /**
     * 线路名称重复
     */
    POI_LINE_REDO(2774, "线路名称重复"),

    /**
     * 线路信息不存在
     */
    POI_LINE_NULL(2777, "线路信息不存在"),

    /**
     * 标题名称重复啦
     */
    NEWS_TITLE_REDO(2780, "标题名称重复啦"),

    /**
     * 分类名称重复啦
     */
    NEWS_CONFIG_TITLE_REDO(2783, "分类名称重复啦"),

    /**
     * 分类编号重复啦
     */
    NEWS_CONFIG_CODE_REDO(2786, "分类编号重复啦"),

    /**
     * 文章从地球上消失啦~
     */
    NEWS_NULL(2789, "文章从地球上消失啦~"),

    /**
     * 该资讯不支持评论
     */
    NEWS_COMMENT_FORBID(2790, "该资讯不支持评论"),

    /**
     * 退款金额不能超过%s元
     */
    REFUND_AMOUNT_MAX(2792, "退款金额不能超过%s元"),

    /**
     * 该订单已确认
     */
    ORDER_CONFIRM(2795, "该订单已确认"),

    /**
     * 订单已发货,请退货退款
     */
    REFUND_DELIVERY(2798, "订单已发货,只支持退货退款"),

    /**
     * 商户账户更新失败
     */
    ACCOUNT_UPDATE(2801, "商户账户更新失败"),

    /**
     * 商户积分账户更新失败
     */
    SCORE_ACCOUNT_UPDATE(2804, "商户积分账户更新失败"),

    /**
     * 商户授权二维码已过期,请重新生成
     */
    MERCHANT_CODE_EXPIRE(2807, "商户授权二维码已过期,请重新生成"),

    /**
     * 商户信息未查询到
     */
    MERCHANT_NULL(2810, "商户信息未查询到"),

    /**
     * 商户已绑定微信号
     */
    MERCHANT_BINDING(2813, "商户已绑定微信号"),

    /**
     * 商户未绑定微信号,无需解绑
     */
    MERCHANT_NO_BIND(2816, "商户未绑定微信号,无需解绑"),

    /**
     * 商户可用余额不足
     */
    MERCHANT_ACCOUNT_USE(2819, "商户可用余额不足"),

    /**
     * 商户支付冻结余额不足
     */
    MERCHANT_ACCOUNT_PAY(2822, "商户支付冻结余额不足"),

    /**
     * 商户提现冻结余额不足
     */
    MERCHANT_ACCOUNT_WITHDRAW(2825, "商户提现冻结余额不足"),

    /**
     * 商户可用积分不足
     */
    MERCHANT_SCORE_USE(2828, "商户可用积分不足"),

    /**
     * 商户冻结积分不足
     */
    MERCHANT_SCORE_PAY(2831, "商户支付冻结积分不足"),

    /**
     * 商户退款冻结积分不足
     */
    MERCHANT_SCORE_WITHDRAW(2834, "商户退款冻结积分不足"),

    /**
     * 页面类型不存在
     */
    VISIT_TYPE_NULL(2837, "页面类型不存在"),

    /**
     * 房间数与入住信息不匹配
     */
    VISITOR_NO_MATCH(2840, "房间数与入住信息不匹配"),

    /**
     * 拼团活动名称重复
     */
    REDO_TITLE_BOOKING(2843, "拼团活动名称重复"),

    /**
     * 该商品已添加拼团活动
     */
    ITEM_BOOKING(2846, "该商品已添加拼团活动"),

    /**
     * 拼团商品不支持购物车下单
     */
    ITEM_MULTIPLE_BOOKING(2849, "拼团商品不支持购物车下单"),

    /**
     * 只有未开始的拼团才支持编辑
     */
    ACTIVITY_NOT_EDIT(2852, "只有未开始的拼团才支持编辑"),

    /**
     * 进行中拼团不能删除
     */
    BOOKING_DELETE(2855, "进行中拼团不能删除"),

    /**
     * 活动时间必须大于当前时间
     */
    BOOKING_GT_TIME(2858, "活动时间必须大于当前时间"),

    /**
     * 拼团活动结束时间不能大于一个月
     */
    BOOKING_GT_MONTH(2861, "拼团活动结束时间不能大于一个月"),

    /**
     * [%s]限购:d%件
     */
    ITEM_CHECK_QUOTA(2864, "[%s]限购:d%件"),

    /**
     * 拼团活动不在有效期
     */
    ITEM_GROUP_NULL(2867, "拼团活动不在有效期"),

    /**
     * 拼团人数已经够啦~
     */
    ITEM_GROUP_COMPLETE(2870, "拼团人数已经够啦~"),

    /**
     * 拼团活动不能重复参加
     */
    ITEM_GROUP_REPEAT(2871, "拼团活动不能重复参加"),

    /**
     * 拼团活动结束啦~
     */
    ITEM_GROUP_OVER(2873, "拼团活动结束啦~"),

    /**
     * 拼团活动不存在
     */
    GROUP_ORDER_NULL(2876, "拼团活动不存在"),

    /**
     * 限时购活动名称重复
     */
    REDO_TITLE_LIMIT(2879, "限时购活动名称重复啦"),

    /**
     * 活动时间必须大于当前时间
     */
    LIMIT_GT_TIME(2882, "活动时间必须大于当前时间"),

    /**
     * 活动时间跨度不能大于一个月
     */
    LIMIT_GT_MONTH(2885, "活动时间跨度不能大于一个月"),

    /**
     * 该商品已存在限时购活动
     */
    LIMIT_ITEM_REDO(2888, "该商品已存在限时购活动"),

    /**
     * 进行中的活动暂无法删除
     */
    LIMIT_UNDERWAY_DELETE(2891, "进行中的活动暂无法删除"),

    /**
     * 限时购商品不存在或已删除
     */
    LIMIT_ITEM_NULL(2894, "限时购商品不存在或已删除"),

    /**
     * 限时购活动删除啦~
     */
    LIMIT_NULL(2897, "限时购活动删除啦~"),

    /**
     * 场馆名称重复啦~
     */
    VENUE_REDO(2900, "场馆名称重复啦~"),

    /**
     * 场馆信息未查询到
     */
    VENUE_NULL(2903, "场馆信息未查询到"),

    /**
     * 场地名称重复啦~
     */
    VENUE_SITE_REDO(2906, "场地名称重复啦~"),

    /**
     * 场地信息未查询到
     */
    VENUE_SITE_NULL(2909, "场地信息未查询到"),

    /**
     * 场次名称重复啦~
     */
    VENUE_SESSION_REDO(2912, "场地名称重复啦~"),

    /**
     * 场次信息未查询到
     */
    VENUE_SESSION_NULL(2915, "场地信息未查询到"),

    /**
     * 该时间段已被预约
     */
    VENUE_STOCK(2918, "该时间段已被预约"),

    /**
     * 该场次已经下架啦~
     */
    VENUE_SITE_DOWN(2921, "该场次已经下架啦~"),

    /**
     * 场馆信息下架啦~
     */
    VENUE_DOWN(2924, "场馆信息下架啦~"),

    /**
     * 不支持跨天预约
     */
    VENUE_SKIP(2927, "不支持跨天预约"),

    /**
     * 不支持多场馆预约
     */
    VENUE_MULTI(2930, "不支持多场馆预约"),

    /**
     * 不支持跨场地预约
     */
    VENUE_SITE_MULTI(2933, "不支持跨场地预约"),

    /**
     * 兑换码名称重复啦
     */
    REDEEM_CODE_REDO(2936, "兑换码名称重复啦"),

    /**
     * 兑换码配置未查询到
     */
    REDEEM_CODE_NULL(2939, "兑换码配置未查询到"),

    /**
     * 兑换码已发放,无法删除
     */
    REDEEM_CODE_DELETE(2942, "兑换码已发放,无法删除"),

    /**
     * 兑换码已发放,无法修改
     */
    REDEEM_CODE_UPDATE(2945, "兑换码已发放,无法修改"),

    /**
     * 兑换码已发放
     */
    REDEEM_CODE_GENERATE(2948, "兑换码已发放"),

    /**
     * 退款信息未查询到
     */
    REFUND_LOG_NULL(2951, "退款信息未查询到"),

    /**
     * 请勿重复退款
     */
    REFUND_LOG_STATE(2954, "请勿重复退款"),

    /**
     * 请勿重复取消退款
     */
    REFUND_LOG_CANCEL(2957, "请勿重复取消退款"),

    /**
     * 退款进行中,无法取消
     */
    REFUND_LOG_AUDIT(2960, "退款进行中,无法取消"),

    /**
     * 无需退款审核
     */
    REFUND_AUDIT(2963, "%s无需退款审核"),

    /**
     * 兑换码无效
     */
    CD_KEY_INVALID(2966, "兑换码无效"),

    /**
     * 兑换码不在有效期
     */
    CD_KEY_EXPIRE(2969, "兑换码不在有效期"),

    /**
     * 兑换码已使用
     */
    CD_KEY_USED(2972, "兑换码已使用"),

    /**
     * 兑换码不适用该商品
     */
    CD_KEY_STORE_SCOPE(2975, "兑换码不适用该商品"),

    /**
     * 兑换码不适用该商品
     */
    CD_KEY_PRODUCT_SCOPE(2978, "兑换码不适用该商品"),

    /**
     * 请核对当前账户是否为商户
     */
    ACCOUNT_NOT_EXIST(2981, "请核对当前账户是否为商户"),

    /**
     * 拼团活动未查询到
     */
    BOOKING_NULL(2984, "拼团活动未查询到"),

    /**
     * 您已在该拼团中,请勿重复下单
     */
    BOOKING_REDO_ORDER(2987, "您已在该拼团中,请勿重复下单"),

    /**
     * 可提现金额不足
     */
    WITHDRAW_ENOUGH(2990, "可提现金额不足"),

    /**
     * 积分变动记录未查询到
     */
    SCORE_LOG_NULL(2993, "积分变动记录未查询到"),

    /**
     * 资金变动记录未查询到
     */
    ACCOUNT_LOG_NULL(2996, "资金变动记录未查询到"),

    /**
     * 扫码充值记录未查询到
     */
    RECHARGE_LOG_NULL(2999, "扫码充值记录未查询到"),

    /**
     * 充值金额不能小于%s元
     */
    SCORE_RECHARGE_MIN(3000, "充值金额不能小于%s元"),

    /**
     * 充值订单创建失败
     */
    RECHARGE_CREATE_ERROR(3001, "充值订单创建失败"),

    /**
     * 积分余额不足
     */
    MEMBER_SCORE_ERROR(3002, "积分余额不足"),

    /**
     * 提现金额不能小于%s元
     */
    SCORE_WITHDRAW_MIN(3003, "提现金额不能小于%s元"),

    /**
     * 积分奖品不存在
     */
    SCORE_PRIZE_NULL(3005, "积分奖品不存在"),

    /**
     * 中奖数量更新失败
     */
    PRIZE_WIN_ERROR(3008, "中奖数量更新失败"),

    /**
     * 抽奖活动已经结束啦
     */
    LOTTERY_CONFIG_NULL(3011, "抽奖活动已经结束啦"),

    /**
     * 进行中抽奖活动无法直接删除
     */
    LOTTERY_NOT_DELETE(3012, "进行中抽奖活动无法直接删除"),

    /**
     * 订单冻结记录不存在
     */
    FREEZE_LOG_NULL(3014, "订单冻结记录不存在"),

    /**
     * 订单冻结记录已解冻
     */
    FREEZE_LOG_UNFREEZE(3017, "订单冻结记录已解冻"),

    /**
     * 冻结金额和解冻金额不匹配
     */
    FREEZE_LOG_AMOUNT(3020, "冻结金额和解冻金额不匹配"),

    /**
     * 标签名称重复啦~
     */
    TAG_TITLE_REDO(3023, "标签名称重复啦~"),

    /**
     * 标签信息不存在
     */
    TAG_NULL(3026, "标签信息不存在"),

    /**
     * 请选择会员
     */
    TAG_SCOPE_NULL(3029, "请选择会员"),

    /**
     * 请选择会员
     */
    SMS_SCOPE_NULL(3032, "请选择会员"),

    /**
     * 请选择会员
     */
    SCORE_INTEGER(3035, "积分必须的100倍数"),

    /**
     * 积分太多啦~
     */
    SCORE_SURPLUS(3038, "积分太多啦~"),

    /**
     * 您的可用积分不足
     */
    SCORE_NOT_ENOUGH(3041, "您的可用积分不足"),

    /**
     * 商户资金不为零,无法注销
     */
    MERCHANT_HAS_FREEZE(3044, "商户资金不为零,无法注销"),

    /**
     * 商户积分不为零,无法注销
     */
    MERCHANT_SCORE_HAS_FREEZE(3047, "商户积分不为零,无法注销"),

    /**
     * 访问渠道不能为空
     */
    CHANNEL_NULL(3050, "访问渠道不能为空"),

    /**
     * 请选择合适的预约日期
     */
    VISIT_DATE_ILLEGAL(3053, "请选择合适的预约日期"),

    /**
     * 该地址已被店铺占用,请先更换店铺地址
     */
    ADDRESS_OCCUPIED(3056, "该地址已被店铺占用,请先更换店铺地址"),

    /**
     * 收货地址不存在
     */
    MERCHANT_ADDRESS_NULL(3057, "收货地址不存在"),

    /**
     * 该订单尚未支付,不支持退款
     */
    ORDER_NOT_PAY(3059, "该订单尚未支付,不支持退款"),

    /**
     * 只有未支付的订单才可修改单价
     */
    ORDER_NOT_ADJUST(3062, "只有未支付的订单才可修改单价"),

    /**
     * 调整后的实付价应大于0元
     */
    ORDER_LE_ZERO(3065, "调整后的实付价应大于0元"),

    /**
     * 优惠券已经抢完啦~
     */
    COUPON_NULL(3068, "优惠券已经抢完啦~"),

    /**
     * 已经发放的优惠券，无法删除优惠券
     */
    COUPON_BEGIN(3069, "已经发放的优惠券，无法删除优惠券"),

    /**
     * 店铺通用券无需选择指定商品
     */
    COUPON_SCOPE_ILLEGAL(3071, "店铺通用券无需选择指定商品"),

    /**
     * 请选择优惠券使用范围
     */
    COUPON_SCOPE_NULL(3074, "请选择优惠券使用范围"),

    /**
     * 商品下架啦~
     */
    PRODUCT_COUPON_DOWN(3077, "商品下架啦~"),

    /**
     * 请先补全店铺信息
     */
    STORE_NOT_COMPLETE(3080, "请先补全店铺信息"),

    /**
     * 请先补全民宿信息
     */
    HOMESTAY_NOT_COMPLETE(3083, "请先补全民宿信息"),

    /**
     * 请先补全景区信息
     */
    TICKET_NOT_COMPLETE(3086, "请先补全景区信息"),

    /**
     * 店铺信息不存在
     */
    STORE_NOT_EXIST(3089, "店铺信息不存在"),

    /**
     * 支付渠道暂不支持
     */
    PAY_CHANNEL_NULL(3092, "支付渠道暂不支持"),

    /**
     * 支付渠道暂不支持,请刷新页面
     */
    PAY_CHANNEL_REFRESH(3095, "支付渠道暂不支持,请刷新页面"),

    /**
     * 资讯配置删除啦~
     */
    NEWS_CONFIG_NOT_EXIST(3098, "资讯配置删除啦~"),

    /**
     * 请联系管理员配置微信公众号
     */
    MP_NOT_CONFIG(7000, "请联系管理员配置微信公众号"),

    /**
     * 微信网页授权异常
     */
    MP_JS_AUTH(7001, "微信网页授权异常"),

    /**
     * 微信签名生成失败
     */
    MP_JS_TICKET(7002, "微信签名生成失败"),

    /**
     * 请联系管理员配置微信小程序
     */
    MA_NOT_CONFIG(7005, "请联系管理员配置微信小程序"),

    /**
     * 小程序手机号授权异常
     */
    MA_JS_AUTH(7008, "小程序手机号授权异常"),

    /**
     * 小程序手机号授权为空
     */
    MA_AUTH_NULL(7010, "小程序手机号授权为空"),

    /**
     * 小程序手机号授权为空
     */
    MA_SHORT_URL(7012, "小程序生成短链失败"),

    /**
     * 请核对小程序是否开通电商相关类目
     */
    MA_SHORT_URL_PERM(7013, "短链生成失败，请核对小程序是否开通电商相关类目"),

    /**
     * 小程序生成加密链接失败
     */
    MA_ENCRYPT_URL(7015, "小程序生成加密链接失败"),

    /**
     * 小程序二维码生成失败
     */
    MA_QRCODE_ERROR(7018, "小程序二维码生成失败"),

    /**
     * 钉钉客户端初始化异常
     */
    DING_TALK_INIT(8000, "钉钉客户端初始化异常"),

    /**
     * 钉钉客户端初始化异常
     */
    WEB_HOOK_NULL(8001, "请先配置报警webHook"),
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
