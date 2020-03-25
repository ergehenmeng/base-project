package com.eghm.common.utils;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;


/**
 * AES 加解密
 *
 * @author 二哥很猛
 */
@Slf4j
public class AesUtil {

    private static final String ALGORITHM = "AES";

    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static String encrypt(String source, String password) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));
            byte[] bytes = cipher.doFinal(source.getBytes());
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            log.error("AES加密失败 source:[{}], password:[{}]", source, password, e);
            throw new SystemException(ErrorCode.ENCRYPT_ERROR);
        }
    }

    public static String decrypt(String source, String password) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
            byte[] bytes = cipher.doFinal(Base64.decodeBase64(source));
            return new String(bytes);
        } catch (Exception e) {
            log.error("AES解密失败 source:[{}], password:[{}]", source, password, e);
            throw new SystemException(ErrorCode.DECRYPT_ERROR);
        }
    }

    private static SecretKey getSecretKey(String password)throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
        keyGenerator.init(128, random);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encoded = secretKey.getEncoded();
        return new SecretKeySpec(encoded, ALGORITHM);
    }

}
