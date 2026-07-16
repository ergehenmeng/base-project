package com.eghm.foundation.core.service;

@FunctionalInterface
public interface PasswordDecryptor {

    String rsaDecrypt(String rsaStr);
}
