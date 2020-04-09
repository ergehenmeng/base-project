package com.eghm.annotation;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 已经登陆的用户,强制会对返回进行加密操作,如果不需要加密则添加该注解,注意如果通过{@link HttpServletResponse#getWriter()}}则不会进行加密
 *
 * @see com.eghm.configuration.handler.EncryptRespBodyAdviceHandler 加密过程
 * @author 二哥很猛
 */
@Documented
@Target({METHOD})
@Retention(RUNTIME)
public @interface SkipEncrypt {
}
