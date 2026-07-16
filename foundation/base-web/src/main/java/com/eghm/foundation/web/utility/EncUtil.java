package com.eghm.foundation.web.utility;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

/**
 * @author wyb-eghm
 * @since 2026/6/17
 */
public class EncUtil {
    
    public static void main(String[] args) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        encryptor.setPassword("my-secret-key-2024");
        encryptor.setIvGenerator(new RandomIvGenerator());
        System.out.println("ENC(" + encryptor.encrypt("root") + ")");
    }
}
