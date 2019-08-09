package com.fanyin.configuration.security;


/**
 * @author 王艳兵
 * @date 2019/7/9 15:07
 */
public interface PasswordEncoder {

    /**
     * bc加密
     * @param rawPassword 原始密码
     * @return 加密后字符串
     */
    String encode(String rawPassword);

    /**
     * 查看密码是否匹配
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return true 匹配 false 不匹配
     */
    boolean matches(String rawPassword, String encodedPassword);
}
