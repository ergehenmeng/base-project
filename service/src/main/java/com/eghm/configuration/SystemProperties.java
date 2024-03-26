package com.eghm.configuration;

import com.eghm.enums.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/1/29 16:25
 */
@ConfigurationProperties(prefix = SystemProperties.PREFIX)
@Data
@Component
public class SystemProperties {

    static final String PREFIX = "system";

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
     * 平台手续费收款商户
     */
    private Long platformMerchantId;

    /**
     * 移动端 特有配置
     */
    private final ApiProperties api = new ApiProperties();

    /**
     * 管理后台 特有配置
     */
    private final ManageProperties manage = new ManageProperties();

    /**
     * 微信配置
     */
    private final WeChatProperties wechat = new WeChatProperties();

    /**
     * 支付宝配置
     */
    private final AliPayProperties aliPay = new AliPayProperties();

    /**
     * redis配置
     */
    private final Redis redis = new Redis();

    /**
     * 需要进行xss校验的url
     */
    private final List<String> xssUrl = new ArrayList<>();

    /**
     * 极光推送
     */
    private final PushProperties push = new PushProperties();

    /**
     * 报警消息通知
     */
    private final AlarmMsg alarmMsg = new AlarmMsg();

    /**
     * 快递100配置
     */
    private final Express100 express = new Express100();

    @Data
    public static class Express100 {

        /**
         * 授权码
         */
        private String customer;

        /**
         * 授权key
         */
        private String key;
    }

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
            private TokenType tokenType = TokenType.REDIS;

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

            /**
             * 模拟登录的token
             */
            private String mockToken;
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

        /**
         * 模拟登录的token
         */
        private String mockToken;

        /**
         * 模拟登录的渠道
         */
        private Channel mockChannel = Channel.WECHAT;

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
         * 小程序版本, 默认:正式版
         */
        private WeChatVersion miniVersion = WeChatVersion.RELEASE;

        /**
         * 微信支付所在公众号的appId
         */
        private String payAppId;

        /**
         * 微信支付商户号
         */
        private String payMerchantId;

        /**
         * apiV3 秘钥
         */
        private String payApiV3Key;

        /**
         * 商户私钥证书 api_client_key.pem (classpath)
         */
        private String payPrivateKeyPath;

        /**
         * 商户公钥证书 api_client_cert.pem (classpath)
         */
        private String payPrivateCertPath;

        /**
         * apiV3证书序列号
         */
        private String paySerialNo;

        /**
         * 异步通知域名
         */
        private String notifyHost;

    }

    @Data
    public static class AlarmMsg {

        /**
         * 消息通知类型
         */
        private AlarmType alarmType = AlarmType.DEFAULT;

        /**
         * 钉钉消息通知AccessToken或者飞书通知webHook地址
         */
        private String webHook;

        /**
         * 钉钉或飞书的签名(为空表示采用非签名模式)
         */
        private String secret;
    }
}
