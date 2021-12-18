package com.eghm.configuration.encoder;


/**
 * @author 二哥很猛
 * @date 2019/7/9 15:07
 */
public interface Encoder {

    /**
     * bc加密
     *
     * @param rawPassword 原始密码
     * @return 加密后字符串
     */
    String encode(String rawPassword);

    /**
     * 查看密码是否匹配
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return true 匹配 false 不匹配
     */
    boolean match(String rawPassword, String encodedPassword);
}
