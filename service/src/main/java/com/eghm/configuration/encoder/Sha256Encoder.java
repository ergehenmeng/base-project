package com.eghm.configuration.encoder;


import cn.hutool.crypto.SecureUtil;

/**
 * @author 二哥很猛
 * @date 2019/7/10 15:27
 */
public class Sha256Encoder implements Encoder {

    @Override
    public String encode(String rawPassword) {
        return SecureUtil.hmacSha256().digestHex(rawPassword);
    }

    @Override
    public boolean match(String rawPassword, String encodedPassword) {
        return SecureUtil.hmacSha256().digestHex(rawPassword).equals(encodedPassword);
    }

}
