package com.fanyin.common.utils;

import com.fanyin.common.constant.CommonConstant;
import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.ParameterException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * @author 二哥很猛
 * @date 2018/8/6 15:17
 */
@Slf4j
public class Sha256Util {

    /**
     * sha256加密
     * @param message 待加密字符串
     * @return 加密后的字符串
     */
    public static String sha256(String message){
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(message.getBytes(CommonConstant.CHARSET));
            byte[] digest = instance.digest();
            return ByteUtil.byteArrayToHex(digest);
        } catch (Exception e) {
            log.error("sha256加密异常",e);
            throw new ParameterException(ErrorCode.SHA_256_ERROR);
        }
    }

    /**
     * sha256Hmac加密 秘钥是字符串本身
     * @param message 带加密字符串
     * @return 加密后的字符串
     */
    public static String sha256Hmac(String message){
        try {
            Mac instance = Mac.getInstance("HmacSHA256");
            SecretKey secretKey = new SecretKeySpec(message.getBytes(CommonConstant.CHARSET),"HmacSHA256");
            instance.init(secretKey);
            byte[] bytes = instance.doFinal(message.getBytes(CommonConstant.CHARSET));
            return ByteUtil.byteArrayToHex(bytes);
        } catch (Exception e) {
            log.error("HmacSHA256加密异常",e);
            throw new ParameterException(ErrorCode.SHA_256_ERROR);
        }
    }

}
