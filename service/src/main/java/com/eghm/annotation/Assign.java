package com.eghm.annotation;

import java.lang.annotation.*;

/**
 * 标签注解, 无业务逻辑, 纯注解
 * 如果DTO中的某些字段是有后端赋值, 而非前端传递过来的, 则使用此注解进行标注,方便开发人员理解参数的来源
 *
 * @author 殿小二
 * @since 2020/8/25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Assign {

    /**
     * 赋值使用方式
     *
     * @return 赋值来源
     */
    Type value() default Type.DOWN;

    enum Type {

        /**
         * 向下传递 即: 后端赋值数据后给他要调用的方法进行使用
         */
        DOWN,

        /**
         * 向上传递 即: 后端将计算的结果赋值给调用方
         */
        UP
    }
}
