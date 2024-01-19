package com.eghm.annotation;

import java.lang.annotation.*;

/**
 * 仅仅为标签, 用来区分说明该字段是由后端设置进去而非前端传递过来的 一般在dto
 *
 * @author 殿小二
 * @since 2020/8/25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Assign {

    /**
     * 是否是必填字段
     *
     * @return true:需要后端必须设置进去
     */
    boolean required() default true;
}
