package com.eghm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统错误信息枚举 包含系统所以的异常信息
 * 404 500等标准错误码返回前台时,可做特殊处理(多个业务共用一个错误码)
 * 1000+ 数据异常
 * 2000+ 业务异常
 * @author 二哥很猛
 * @date 2018/1/12 16:46
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
    SESSION_TIMEOUT(402,"用户登陆已过期"),

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
     * 应用上下文尚未完全启动
     */
    SPRING_ON_LOADING(505,"Spring上下文尚未完全启动"),

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
     * 读取参数异常
     */
    READ_PARAM_ERROR(1019,"读取参数异常"),

    /**
     * 参数解析异常
     */
    PARAM_VERIFY_ERROR(2000,"参数校验失败"),

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
    OPERATOR_NOT_FOUND(3005,"用户信息不存在"),

    /**
     * 用户已锁定,请联系管理人员
     */
    OPERATOR_LOCKED_ERROR(3006,"用户已锁定,请联系管理人员"),

    /**
     * 用户超时,请重新登陆
     */
    OPERATOR_TIMEOUT(3007,"用户超时,请重新登陆"),

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
    USER_LOGIN_TIMEOUT(3009,"用户登陆过期,请重新登陆"),

    /**
     * 旧密码输入错误
     */
    OPERATOR_PASSWORD_ERROR(3010,"旧密码输入错误"),

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
     * 菜单标示符被占用
     */
    MENU_NID_ERROR(3015,"菜单标示符被占用"),

    /**
     * 用户已在其他设备上登陆
     */
    MULTIPLE_CLIENT_LOGIN(3016,"用户已在其他设备上登陆"),

    /**
     * 用户不存在或已冻结
     */
    USER_NOT_FOUND(3017,"用户不存在或已冻结"),

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
     * 该版本已上架
     */
    VERSION_REDO(3025,"该版本已上架"),

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
     * 审批配置信息为空
     */
    AUDIT_CONFIG_ERROR(3035,"审批配置信息为空"),

    /**
     * 审批申请信息未查询到
     */
    AUDIT_APPLY_NULL(3038,"审批申请信息未查询到"),

    /**
     * 该申请已审批
     */
    AUDIT_REDO(3040,"该申请已审批"),

    /**
     * 暂无审批权限
     */
    AUDIT_NO_ACCESS(3042,"暂无审批权限"),

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
    USER_ADDRESS_NULL(3062, "用户地址未查询到"),

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
     * 支付响应错误
     */
    PAY_ERROR(3111, "支付响应错误"),

    /**
     * 微信公众号尚未配置
     */
    MP_NOT_CONFIG(4000, "微信公众号尚未配置"),

    /**
     * 微信网页授权异常
     */
    MP_JS_AUTH(4001, "微信网页授权异常"),
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
