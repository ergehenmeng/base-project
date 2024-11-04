package com.eghm.configuration.encoder;


import cn.hutool.crypto.SecureUtil;

/**
 * @author 二哥很猛
 * @since 2019/7/10 15:27
 */
public class Sha256Encoder implements Encoder {

    private static final String SALT = "p5kv0oga7p8j0zq6p1tz95j2jnt1a5wv";

    @Override
    public String encode(String rawPassword) {
        return SecureUtil.hmacSha256(SALT).digestHex(rawPassword);
    }

    @Override
    public boolean match(String rawPassword, String encodedPassword) {
        return SecureUtil.hmacSha256(SALT).digestHex(rawPassword).equals(encodedPassword);
    }

}
