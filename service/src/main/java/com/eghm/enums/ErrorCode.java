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
    FORBIDDEN_ACCESS(405, "暂无访问权限"),

    /**
     * 系统繁忙,请稍后再试
     */
    SYSTEM_ERROR(500, "系统繁忙,请稍后再试"),

    /**
     * 应用上下文尚未完全启动
     */
    SPRING_ON_LOADING(505, "Spring上下文尚未完全启动"),

    /**
     * 数据转换异常
     */
    JSON_FORMAT_ERROR(1000, "数据转换异常"),

    /**
     * 地址不支持%s请求
     */
    METHOD_NOT_SUPPORTED(1010, "地址不支持%s请求"),

    /**
     * 请求参数非法
     */
    REQUEST_PARAM_ILLEGAL(1014, "请求参数非法"),

    /**
     * 签名信息为空
     */
    SIGNATURE_ERROR(1018, "签名信息为空"),

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
     * 模板解析失败
     */
    TEMPLATE_RENDER_ERROR(2033, "模板解析失败"),

    /**
     * 手机号码被占用
     */
    MOBILE_REDO(2036, "手机号码被占用"),

    /**
     * 账户名被占用
     */
    USER_NAME_REDO(2037, "账户名被占用"),

    /**
     * 系统默认角色,禁止删除
     */
    ROLE_FORBID_DELETE(2039, "系统默认角色,禁止删除"),

    /**
     * 角色名称重复
     */
    ROLE_NAME_REDO(2042, "角色名称重复"),

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
     * 手机号不存在
     */
    USER_MOBILE_NULL(2116, "手机号不存在"),

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
     * 验证码无效或已过期
     */
    LOGIN_SMS_CODE_EXPIRE(2117, "验证码无效或已过期"),

    /**
     * 验证次数太多,请重新发送验证码
     */
    SMS_CODE_VERIFY_ERROR(2118, "验证次数太多,请重新发送验证码"),

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
     * 账号名被占用
     */
    ACCOUNT_REGISTER_REDO(2142, "账号名被占用"),

    /**
     * cron表达式配置错误
     */
    CRON_CONFIG_ERROR(2144, "cron表达式配置错误"),

    /**
     * 新设备登陆校验
     */
    NEW_DEVICE_LOGIN(2153, "新设备登陆校验"),

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
     * 站内信模板未配置
     */
    IN_MAIL_NULL(2201, "站内信模板未配置"),

    /**
     * 未知支付方式(微信)
     */
    UNKNOWN_PAY_TYPE(2213, "未知支付方式"),

    /**
     * 订单查询失败
     */
    ORDER_QUERY_ERROR(2216, "订单查询失败"),

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
     * 标题名称重复啦
     */
    NEWS_TITLE_REDO(2780, "标题名称重复啦"),

    /**
     * 公告信息未查询到
     */
    NOTICE_NOT_NULL(2507, "公告信息下架啦~"),

    /**
     * 该账号已禁用,详细情况请联系客服
     */
    MEMBER_LOGIN_FORBID(2678, "账号已禁用,详细情况请联系客服"),

    /**
     * 红包金额太小啦~
     */
    RED_PACKET_ERROR(2654, "红包金额太小啦~"),

    /**
     * 单位名称重复啦
     */
    AUTH_TITLE_REDO(2747, "单位名称重复啦"),

    /**
     * 访问渠道不能为空
     */
    CHANNEL_NULL(3050, "访问渠道不能为空"),

    /**
     * 公告信息未查询到
     */
    NOTICE_NOT_FOUND(2504, "公告信息未查询到"),

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
