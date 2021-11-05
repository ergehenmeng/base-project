package com.eghm.model.annotation;

import java.lang.annotation.*;

/**
 * 标签 表示该字段不是由前台传递过来而是由后台设置进去,主要作用于dto类上
 * @author 殿小二
 * @date 2020/8/25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Sign {
}
