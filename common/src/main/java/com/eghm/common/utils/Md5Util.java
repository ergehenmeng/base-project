package com.eghm.common.utils;

import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.ParameterException;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * MD5摘要算法
 *
 * @author 二哥很猛
 */
@Slf4j
public class Md5Util {

    /**
     * md5加密
     *
     * @param str 待加密的字符串
     * @return 加密过后的字符串 大写
     */
    public static String md5(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes(CommonConstant.CHARSET));
            byte[] bytes = digest.digest();
            return ByteUtil.byteArrayToHex(bytes);
        } catch (Exception e) {
            log.error("MD5加密异常", e);
            throw new ParameterException(ErrorCode.ENCRYPT_ERROR);
        }
    }


}
