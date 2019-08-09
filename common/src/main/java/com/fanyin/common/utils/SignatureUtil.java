package com.fanyin.common.utils;

import com.fanyin.common.constant.CommonConstant;

/**
 * 签名工具类
 * @author 二哥很猛
 * @date 2018/8/15 15:33
 */
public class SignatureUtil {

    /**
     * 时间戳签名 加密方式 sha256Hmac(appKey+timestamp)
     * @param salt 盐
     * @return 签名后的数据
     */
    public static String sign(String salt){
        return Sha256Util.sha256Hmac(CommonConstant.APP_KEY + salt);
    }

}
