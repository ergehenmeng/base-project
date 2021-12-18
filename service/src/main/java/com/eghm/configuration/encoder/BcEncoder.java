package com.eghm.configuration.encoder;

import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Pattern;

/**
 * @author 二哥很猛
 * @date 2019/7/9 15:57
 */
@Slf4j
public class BcEncoder implements Encoder {

    private static final Pattern PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    /**
     * bc加密
     *
     * @param rawPassword 原始密码
     * @return 加密后字符串
     */
    @Override
    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    /**
     * 查看密码是否匹配
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return true 匹配 false 不匹配
     */
    @Override
    public boolean match(String rawPassword, String encodedPassword) {

        if (encodedPassword == null || encodedPassword.length() == 0) {
            return false;
        }

        if (!PATTERN.matcher(encodedPassword).matches()) {
            log.warn("非BCrypt算法");
            return false;
        }
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
