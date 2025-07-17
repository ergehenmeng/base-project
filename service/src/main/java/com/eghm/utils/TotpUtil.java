package com.eghm.utils;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * TOTP 双因子验证工具类
 *
 * @author 二哥很猛
 * @since 2025/7/17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TotpUtil {

    /**
     * 创建密钥
     *
     * @return 秘钥
     */
    public static GoogleAuthenticatorKey createSecretKey() {
        GoogleAuthenticator authenticator = new GoogleAuthenticator();
        return authenticator.createCredentials();
    }

    /**
     * 验证验证码是否合法
     *
     * @param secretKey 秘钥
     * @param code 一次性验证码
     * @return true: 匹配 false: 不匹配
     */
    public static boolean verify(String secretKey, Integer code) {
        return new GoogleAuthenticator().authorize(secretKey, code);
    }

}
