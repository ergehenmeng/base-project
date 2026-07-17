package com.eghm.foundation.core.service;

@FunctionalInterface
public interface PasswordDecryptor {
    
    /**
     * rsa 私钥解密
     *
     * @param rsa rsa加密后的字符串
     * @return rsa 解密后的字符串
     */
    String rsaDecrypt(String rsa);
}
