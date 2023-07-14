package com.eghm.configuration;

import com.eghm.enums.Env;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 二哥很猛
 * @date 2022/1/29 16:25
 */
@ConfigurationProperties(prefix = SystemProperties.PREFIX)
@Data
@Component
public class SystemProperties {

    static final String PREFIX = "system";

    /**
     * 移动端 特有配置
     */
    private final ApiProperties api = new ApiProperties();

    /**
     * 管理后台 特有配置
     */
    private final ManageProperties manage = new ManageProperties();

    /**
     * 自动针对@ResponseBody进行包装
     */
    private String wrapperBasePackage;

    /**
     * 上传文件的路径
     */
    private String uploadDir;

    /**
     * 两次提交间隔时间,只针对post请求, 单位:毫秒
     */
    private Long submitInterval = 1000L;

    /**
     * 系统运行环境(默认准生产环境)
     */
    private Env env = Env.PRE;

    /**
     * 微信配置
     */
    private WeChatProperties wechat = new WeChatProperties();

    /**
     * 支付宝配置
     */
    private AliPayProperties aliPay = new AliPayProperties();

    /**
     * redis配置
     */
    private Redis redis = new Redis();

    /**
     * 极光推送
     */
    private PushProperties push = new PushProperties();

    /**
     * 钉钉消息通知
     */
    private DingTalk dingTalk = new DingTalk();

    @Data
    public static class Redis {

        /**
         * 长过期时间 默认30分钟
         */
        private Integer longExpire = 1800;

        /**
         * 短过期时间 默认10分钟
         */
        private Integer shortExpire = 600;

        /**
         * 超短过期时间 默认1分钟
         */
        private Integer smallExpire = 60;
    }

    @Data
    public static class ManageProperties {

        /**
         * token相关配置
         */
        private Jwt jwt = new Jwt();

        /**
         * 权限
         */
        private Security security = new Security();

        @Data
        public static class Security {

            /**
             * 不进行认证和校验和权限校验的链接
             */
            private String[] ignore = new String[]{};

            /**
             * 进行认证但不进行权限校验的链接
             */
            private String[] ignoreAuth = new String[]{};
        }

        @Data
        public static class Jwt {

            /**
             * 在请求头中key
             */
            private String header = "token";

            /**
             * token前缀
             */
            private String prefix = "Bearer ";

            /**
             * token默认过期时间 默认两个小时过期
             */
            private Integer expire = 3600 * 2;

            /**
             * 刷新token的过期时间 默认7天过期
             */
            private Integer refreshExpire = 3600 * 24 * 7;

            /**
             * token加密秘钥
             */
            private String secretKey;
        }
    }

    @Data
    public static class ApiProperties {

        /**
         * 系统版本号
         */
        private String version;

        /**
         * 加密秘钥,用于数据库字段加密
         */
        private String secretKey = "7ec9ebaf378217d94df28342a4ff007b";

    }

    @Data
    public static class PushProperties {

        /**
         * 推送地址
         */
        private String url;

        /**
         * true:生产环境 false:测试环境
         */
        private Boolean env;

        /**
         * 推送key
         */
        private String masterSecret;

        /**
         * 推送appKey
         */
        private String appKey;
    }

    @Data
    public static class AliPayProperties {

        /**
         * 支付appId
         */
        private String appId;

        /**
         * 私钥
         */
        private String privateKey;

        /**
         * 公钥 (非正式模式)
         */
        private String publicKey;

        /**
         * 异步通知域名
         */
        private String notifyHost;

        /**
         * 支付异步通知地址
         */
        private String payNotifyUrl;

        /**
         * 退款异步通知地址
         */
        private String refundNotifyUrl;

        /**
         * AES密钥（可选）
         */
        private String encryptKey;
    }

    @Data
    public static class WeChatProperties {

        /**
         * 公众号appId
         */
        private String mpAppId;

        /**
         * 公众号appSecret
         */
        private String mpAppSecret;

        /**
         * 小程序appId
         */
        private String miniAppId;

        /**
         * 小程序appId
         */
        private String miniAppSecret;

        /**
         * 微信支付所在公众号的appId
         */
        private String payAppId;

        /**
         * 微信支付商户号
         */
        private String payMerchantId;

        /**
         * 微信支付商户密钥
         */
        private String payMerchantKey;

        /**
         * apiV3 秘钥
         */
        private String payApiV3Key;

        /**
         * 私钥路径 支持classpath
         */
        private String payPrivateKeyPath;

        /**
         * apiV3证书序列号
         */
        private String paySerialNo;

        /**
         * 异步通知域名
         */
        private String notifyHost;

        /**
         * 支付异步通知地址
         */
        private String payNotifyUrl;

        /**
         * 退款异步通知地址
         */
        private String refundNotifyUrl;
    }

    @Data
    public static class DingTalk {

        /**
         * 接口地址
         */
        private String url;

        /**
         * 钉钉消息推送通知
         */
        private String accessToken;

        /**
         * 签名(为空表示采用非签名模式)
         */
        private String secret;
    }
}
