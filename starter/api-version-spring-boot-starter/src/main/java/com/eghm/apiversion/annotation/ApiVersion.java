package com.eghm.apiversion.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明 Controller 或处理方法支持的 API 起始版本。
 *
 * @since 2025/6/15
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    /**
     * 当前接口实现支持的起始版本。<br>
     * 默认比较器支持 "1.0.0" 至 "99.99.99" 格式 <br>
     *
     * @return 接口起始版本，默认为 "1.0.0"
     */
    String value() default "1.0.0";

    /**
     * 当前接口版本是否已废弃。<br>
     * 设置为 true 后，拦截器会在响应中写入可配置的废弃标识头；<br>
     * 该属性只提供客户端提示，不阻止接口继续访问 <br>
     *
     * @return true 表示当前版本已废弃，默认为 false
     */
    boolean deprecated() default false;

    /**
     * 接口废弃提示消息。<br>
     * 仅当 {@link #deprecated()} 为 true 且当前值非空时，才会写入废弃消息响应头
     *
     * @return 废弃提示消息，默认为空字符串
     */
    String deprecatedMessage() default "";
}
