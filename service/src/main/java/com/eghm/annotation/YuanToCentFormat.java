package com.eghm.annotation;

import java.lang.annotation.*;

/**
 * GET请求数据绑定时使用Integer接受前端传递的数值，自动转换成分
 *
 * @author 二哥很猛
 * @since 2023/10/8
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface YuanToCentFormat {
}
