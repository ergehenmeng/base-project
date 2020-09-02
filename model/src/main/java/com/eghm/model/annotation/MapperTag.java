package com.eghm.model.annotation;

import java.lang.annotation.*;

/**
 * 表示该字段或该类是直接由数据库映射而来的
 * @author 殿小二
 * @date 2020/9/2
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface MapperTag {
}
