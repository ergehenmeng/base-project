package com.eghm.annotation;

import java.lang.annotation.*;

/**
 * 枚举序列化时返回前端的描述信息, 需要结合 @JsonSerialize(using = EnumDescSerializer.class) 使用
 *
 * @author 二哥很猛
 * @since 2025/1/9
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonDesc {
}
