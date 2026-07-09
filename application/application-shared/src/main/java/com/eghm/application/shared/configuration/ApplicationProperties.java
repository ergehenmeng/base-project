package com.eghm.application.shared.configuration;

import com.eghm.enums.AlarmType;
import com.eghm.enums.Env;
import com.eghm.enums.LoginType;
import com.eghm.enums.SmsChannel;
import com.eghm.enums.TokenType;
import com.eghm.enums.UploadType;
import com.eghm.enums.WeChatVersion;
import com.google.code.kaptcha.text.impl.DefaultTextCreator;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/1/29 16:25
 */
@Data
@Component
@ConfigurationProperties(prefix = ApplicationProperties.PREFIX)
public class ApplicationProperties {

    static final String PREFIX = "application";

    /**
     * 上传文件的绝对路径 (必填)
     */
    private String uploadPath;

    /**
     * 上传文件时父文件夹名称
     */
    private String uploadFolder = "image";

    /**
     * 上传文件方式
     */
    private UploadType uploadType = UploadType.SYSTEM;

    /**
     * 系统运行环境(默认准生产环境)
     */
    private Env env = Env.PROD;

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
    private final AliProperties ali = new AliProperties();

    /**
     * 需要进行xss校验的url
     */
    private final List<String> xssUrl = new ArrayList<>();

    /**
     * 报警消息通知
     */
    private final Alarm alarm = new Alarm();

    /**
     * 短信配置
     */
    private final Sms sms = new Sms();

    /**
     * 私钥(用于密码解密)
     */
    private String privateKey;

    /**
     * 公钥(用于密码解密)
     */
    private String publicKey;

    @Data
    public static class Sms {

        /**
         * 短信渠道-不配置默认只打印日志
         */
        private SmsChannel channel;

        /**
         * 签名信息
         */
        private String signName;

        /**
         * AccessKey ID
         */
        private String keyId;

        /**
         * AccessKey Secret
         */
        private String secretKey;

    }

    @Data
    public static class ManageProperties {

        /**
         * token相关配置
         */
        private Token token = new Token();

        /**
         * 不需要token校验的url
         */
        private String[] whiteList = new String[]{};

        /**
         * 验证码类型
         */
        private Class<? extends DefaultTextCreator> captchaType = MathCaptchaProducer.class;

        /**
         * 登录方式 1:密码登录 2:短信登录 4:二维码登录(未实现)<br>
         * 注意: 支持同时多种方式登录
         * 最终登录方式如下: 1、2、3(1+2)、4、5(1+4)、6(2+4)、7(1+2+4)
         */
        private List<LoginType> loginTypes = new ArrayList<>();

        @Data
        public static class Token {

            /**
             * token实现类型
             */
            private TokenType tokenType = TokenType.REDIS;

            /**
             * token前缀
             */
            private String tokenPrefix = "Bearer ";

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
    public static class AliProperties {

        /**
         * 支付宝配置
         */
        private final AliPay pay = new AliPay();

        /**
         * 阿里oss
         */
        private final AliOss oss = new AliOss();

    }

    @Data
    public static class AliOss {

        /**
         * oss域名
         */
        private String endpoint;

        /**
         * Bucket所在地域
         */
        private String regionName;

        /**
         * bucket名称
         */
        private String bucketName;

        /**
         * accessKeyId
         */
        private String keyId;

        /**
         * accessKeySecret
         */
        private String keySecret;

        /**
         * 访问域名
         */
        private String accessDomain;

    }

    @Data
    public static class AliPay {

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
         * AES密钥（可选）
         */
        private String encryptKey;
    }

    @Data
    public static class WxMp {

        /**
         * 公众号appId
         */
        private String appId;

        /**
         * 公众号appSecret
         */
        private String appSecret;

    }

    @Data
    public static class WxPay {

        /**
         * 商户号
         */
        private String mchId;

        /**
         * 商户私钥证书 api_client_key.pem (classpath)
         */
        private String privateKeyPath;

        /**
         * 商户证书序列号
         */
        private String serialNo;

        /**
         * apiV3 秘钥
         */
        private String apiV3Key;
    }

    @Data
    public static class WxMa {

        /**
         * 小程序appId
         */
        private String appId;

        /**
         * 小程序appId
         */
        private String appSecret;

        /**
         * 小程序版本, 默认:正式版
         */
        private WeChatVersion version = WeChatVersion.RELEASE;

    }

    @Data
    public static class WeChatProperties {

        /**
         * 公众号配置
         */
        private final WxMp mp = new WxMp();

        /**
         * 小程序配置
         */
        private final WxMa ma = new WxMa();

        /**
         * 微信支付配置
         */
        private final WxPay pay = new WxPay();

    }

    @Data
    public static class Alarm {

        /**
         * 消息通知类型
         */
        private AlarmType type;

        /**
         * 钉钉消息通知AccessToken或者飞书、企业微信的通知webHook地址
         */
        private String webHook;

        /**
         * 钉钉或飞书的签名(为空表示采用非签名模式)
         */
        private String secret;
    }
}
