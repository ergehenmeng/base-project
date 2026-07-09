package com.eghm.infrastructure.persistence.mybatis.permission;

import java.lang.annotation.*;

/**
 * aop 数据权限,借鉴 <a href="https://gitee.com/y_project/RuoYi">RuoYi</a> 开源项目
 * 涉及到数据权限时,数据列名称必须包含 dept_code(用户所属部门编号)字段及user_id(用户id)字段,建议对dept_code,user_id列创建索引
 * 如果要使用数据权限,则需要在指定的方法上添加该注解(Mapper层),在SQL中按需拼接${dataScope}即可.
 * 注意: 最好判断是否为空,为空则不拼接. 且不支持Mybatis-Plus原生的SQL查询
 *
 * @author 殿小二
 * @see DataScopeAspect
 * @since 2020/8/14
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {

    /**
     * 涉及数据行的表别名
     *
     * @return 例如: select * from data t where t.dept_code = x  则该别名为 t
     */
    String alias() default "";
}
