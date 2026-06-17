package com.eghm.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @author wyb-eghm
 * @since 2026/6/17
 */
public class EncUtil {
    
    public static void main(String[] args) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("my-secret-key-2024");
        System.out.println("ENC(" + encryptor.encrypt("root") + ")");
    }
}
