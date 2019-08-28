package com.fanyin.configuration.security;


import com.fanyin.common.utils.Sha256Util;

/**
 * @author 二哥很猛
 * @date 2019/7/10 15:27
 */
public class Sha256Encoder implements Encoder {

    @Override
    public String encode(String rawPassword) {
        return Sha256Util.sha256Hmac(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return Sha256Util.sha256Hmac(rawPassword).equals(encodedPassword);
    }

}
