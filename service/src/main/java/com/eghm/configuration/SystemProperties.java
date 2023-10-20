package com.eghm.configuration;

import com.eghm.enums.Env;
import com.eghm.enums.TokenType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
     * 上传文件的路径
     */
    private String uploadDir;

    /**
     * 两次提交间隔时间,只针对post请求, 单位:秒
     */
    private Long submitInterval = 1L;

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
     * 需要进行xss校验的url
     */
    private List<String> xssUrl = new ArrayList<>();

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
        private Token token = new Token();

        /**
         * 权限
         */
        private Security security = new Security();

        @Data
        public static class Security {

            /**
             * 不进行登陆检验和权限校验
             */
            private String[] skipAuth = new String[]{};

            /**
             * 需要登陆但不进行权限校验
             */
            private String[] skipPerm = new String[]{};
        }

        @Data
        public static class Token {

            /**
             * token实现类型
             */
            private TokenType tokenType;

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
             * token加密秘钥(jwt专用)
             */
            private String jwtSecret;
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
        private String payNotifyUrl = "/webapp/notify/ali/pay";

        /**
         * 退款异步通知地址
         */
        private String refundNotifyUrl = "/webapp/notify/ali/refund";

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
        private String payNotifyUrl = "/webapp/notify/weChat/pay";

        /**
         * 退款异步通知地址
         */
        private String refundNotifyUrl = "/webapp/notify/weChat/refund";
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
