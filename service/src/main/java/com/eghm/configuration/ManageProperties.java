package com.eghm.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wyb
 * @date 2022/1/28 18:30
 */
@ConfigurationProperties(prefix = ManageProperties.PREFIX)
@Component
@Data
public class ManageProperties {

    static final String PREFIX = "manage";

    /**
     * token相关配置
     */
    private Jwt jwt = new Jwt();


    @Data
    public static class Jwt {

        /**
         * 在请求头中key
         */
        private String header = "Authorization";

        /**
         * token前缀
         */
        private String prefix = "Bearer ";

        /**
         * token默认过期时间 默认两个小时过期
         */
        private Integer expire = 3600 * 2;

        /**
         * 刷新token的过期时间 默认7天过期
         */
        private Integer refreshExpire = 3600 * 24 * 7;

        /**
         * token加密秘钥
         */
        private String secretKey;
    }
}
