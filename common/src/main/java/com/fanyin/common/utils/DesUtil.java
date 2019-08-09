package com.fanyin.common.utils;

import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.ParameterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;


/**
 * DES加密 3DES加密
 * @author 二哥很猛
 * @date 2018/1/17 15:26
 */
@Slf4j
public class DesUtil {

    /**
     * DES算法填充方式
     */
    private static final String DES_PADDING = "DES/ECB/PKCS5Padding";

    /**
     * 3DES算法填充方式
     */
    private static final String DES3_PADDING = "DESede/ECB/PKCS5Padding";

    /**
     * 加密方式 3DES
     */
    private static final String DES3 = "DESede";

    /**
     * 加密方式 DES
     */
    private static final String DES = "DES";
    /**
     * 默认秘钥
     */
    private static final String DEFAULT_PASSWORD = "15d3eb423c1e3c7872e84e2806051e31";

    /**
     * des默认秘钥长度 超过会自动截取
     */
    private static final int DES_LENGTH = 8;

    /**
     * 3des默认秘钥长度 12位或24位
     */
    private static final int DES3_LENGTH = 24;


    /**
     * 3des解密 采用默认密匙
     * @param str 待解密的字符串
     * @return 解密过的字符串
     */
    public static String decrypt3Des(String str){
        return decrypt(str,DES3,null);
    }

    /**
     * 3des解密
     * @param str 待解密的字符串
     * @param password 秘钥
     * @return 解密过的字符串
     */
    public static String decrypt3Des(String str,String password){
        return decrypt(str,DES3,password);
    }

    /**
     * des解密 采用默认密匙
     * @param str 待解密的字符串
     * @return 解密过的字符串
     */
    public static String decrypt(String str){
        return decrypt(str,DES,null);
    }

    /**
     * des解密
     * @param str 待解密的字符串
     * @param password 秘钥
     * @return 解密过的字符串
     */
    public static String decrypt(String str,String password){
        return decrypt(str,DES,password);
    }
    /**
     * des加密
     * @param str 待加密字符串
     * @param password 秘钥 长度应超过8位
     * @return 加密过的字符串
     */
    public static String encrypt(String str,String password){
        return encrypt(str,DES,password);
    }

    /**
     * des加密 采用默认秘钥
     * @param str 待加密字符串
     * @return 加密过的字符串
     */
    public static String encrypt(String str){
        return encrypt(str,DES,null);
    }

    /**
     * 3DES加密
     * @param str 待加密字符串
     * @param password 秘钥 长度应超过8位
     * @return 加密过的字符串
     */
    public static String encrypt3Des(String str,String password){
        return encrypt(str,DES3,password);
    }

    /**
     * 3DES加密 采用默认加密方式
     * @param str 待加密字符串
     * @return 加密过的字符串
     */
    public static String encrypt3Des(String str){
        return encrypt(str,DES3,null);
    }

    /**
     * des加密
     * @param str 待加密的字符串
     * @param desType des填充方式
     * @param password 秘钥
     * @return 加密后的字符串
     */
    private static String encrypt(String str,String desType,String password){
        try {
            SecretKey convertSecretKey = getSecretKey(password,desType);
            Cipher cipher =getCipher(desType);
            cipher.init(Cipher.ENCRYPT_MODE,convertSecretKey);
            byte[] bytes = cipher.doFinal(str.getBytes(Charset.forName("UTF-8")));
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            log.error("DES加密失败",e);
            throw new ParameterException(ErrorCodeEnum.ENCRYPT_ERROR);
        }
    }

    /**
     * des解密
     * @param str 待解密的字符串
     * @param desType  加密填充方式
     * @param password 秘钥
     * @return 解密后的字符串
     */
    private static String decrypt(String str,String desType,String password){
        try {
            SecretKey convertSecretKey = getSecretKey(password,desType);
            Cipher cipher = getCipher(desType);
            cipher.init(Cipher.DECRYPT_MODE,convertSecretKey);
            byte[] params = Base64.decodeBase64(str);
            byte[] bytes = cipher.doFinal(params);
            return new String(bytes,Charset.forName("UTF-8"));
        } catch (Exception e) {
            log.error("DES解密失败",e);
            throw new ParameterException(ErrorCodeEnum.DECRYPT_ERROR);
        }
    }

    /**
     * 生成加解密对象
     * @param desType 加密方式
     * @return cipher
     */
    private static Cipher getCipher(String desType){
        try {
            if(DES.equals(desType)){
                return Cipher.getInstance(DES_PADDING);
            }
            return Cipher.getInstance(DES3_PADDING);
        } catch (Exception e) {
            log.error("生成Cipher加解密对象异常",e);
            throw new ParameterException(ErrorCodeEnum.ENCRYPT_DECRYPT_ERROR);
        }
    }

    /**
     * 生成秘钥key DES与3DES生成方式不一样
     * @param password 秘钥长度不能低于8位
     * @param desType  填充方式
     * @return 生成的秘钥key对象
     */
    private static SecretKey getSecretKey(String password,String desType){
        try {
            if(DES3.equals(desType)){
                return new SecretKeySpec(getKeyByte(password,desType),desType);
            }
            DESKeySpec desKey = new DESKeySpec(getKeyByte(password,desType));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(desType);
            return keyFactory.generateSecret(desKey);
        } catch (Exception e) {
            log.error("DES获取秘钥key失败",e);
            throw new ParameterException(ErrorCodeEnum.ENCRYPT_DECRYPT_ERROR);
        }
    }

    /**
     * 将秘钥转换为3des规定的位数24位或者des 8位
     * @param password 秘钥
     * @param desType 算法填充方式
     * @return 秘钥的byte数组
     */
    private static byte[] getKeyByte(String password,String desType){
        if (password == null){
            password = DEFAULT_PASSWORD;
        }
        int length = DES3.equals(desType) ? DES3_LENGTH : DES_LENGTH;
        byte[] keys = new byte[length];

        byte[] temp = password.getBytes(Charset.forName("UTF-8"));
        if (keys.length > temp.length){
            //不够 后面自动补零
            System.arraycopy(temp,0,keys,0,temp.length);
        }else{
            System.arraycopy(temp,0,keys,0,keys.length);
        }
        return keys;
    }


}
