package com.fanyin.common.utils;


import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.ParameterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.Charset;

/**
 * PBE加密 对比DES 增加盐作为混淆
 * @author 二哥很猛
 * @date 2018/1/17 15:30
 */
@Slf4j
public class PbeUtil {

    /**
     * 盐 直接写死
     */
    private static final byte[] SALT = "PbeUtil".getBytes(Charset.forName("UTF-8"));

    /**
     * 迭代次数
     */
    private final static int SALT_COUNT = 100;

    /**
     * pbe加密填充方式
     */
    private static final String PBE = "PBEWITHMD5andDES";
    /**
     * 默认的口令密码
     */
    private static final String PASSWORD = "c2839f35e17b040e6f7cbfe35b732339";


    /**
     * pbe加密
     * @param str   要加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String str){
        return encrypt(str,null);
    }

    /**
     * pbe加密
     * @param str   要加密的字符串
     * @param password 秘钥串
     * @return 加密后的字符串
     */
    public static String encrypt(String str,String password){
        try {
            PBEParameterSpec spec = getSpec();
            Cipher cipher = Cipher.getInstance(PBE);
            SecretKey secretKey = getSecretKey(password);
            cipher.init(Cipher.ENCRYPT_MODE,secretKey,spec);
            byte[] bytes = cipher.doFinal(str.getBytes(Charset.forName("UTF-8")));
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            log.error("pbe加密失败",e);
            throw new ParameterException(ErrorCode.ENCRYPT_ERROR);
        }
    }

    /**
     * 生成pbe的参数校验码 盐
     * @return 盐对象
     */
    private static PBEParameterSpec getSpec(){
        return new PBEParameterSpec(SALT,SALT_COUNT);
    }
    /**
     * 生成秘钥key
     * @param password 秘钥
     * @return key
     */
    private static SecretKey getSecretKey(String password){
        if(password == null){
            password = PASSWORD;
        }
        try {
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory factory = SecretKeyFactory.getInstance(PBE);
            return factory.generateSecret(pbeKeySpec);
        } catch (Exception e) {
            throw new ParameterException(ErrorCode.ENCRYPT_DECRYPT_ERROR);
        }
    }
    /**
     * pbe解密
     * @param str   要解密的字符串
     * @param password 秘钥串
     * @return 解密后的字符串
     */
    public static String decrypt(String str,String password){

        try {
            PBEParameterSpec spec = getSpec();
            Cipher cipher = Cipher.getInstance(PBE);
            SecretKey secretKey = getSecretKey(password);
            cipher.init(Cipher.DECRYPT_MODE,secretKey,spec);
            byte[] bytes = Base64.decodeBase64(str);
            byte[] result = cipher.doFinal(bytes);
            return new String(result,Charset.forName("UTF-8"));
        } catch (Exception e) {
            log.error("pbe解密失败",e);
            throw new ParameterException(ErrorCode.DECRYPT_ERROR);
        }
    }

    /**
     * pbe解密
     * @param str   要解密的字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String str){
        return decrypt(str,null);
    }
}
