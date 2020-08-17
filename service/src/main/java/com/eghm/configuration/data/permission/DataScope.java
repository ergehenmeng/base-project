package com.eghm.configuration.data.permission;

import java.lang.annotation.*;

/**
 * aop 数据权限,借鉴 <a href='https://gitee.com/y_project/RuoYi'>RuoYi</a> 开源项目
 * 涉及到数据权限时,数据列名称必须包含 dept_code(用户所属部门编号)字段及operator_id(用户id)字段,建议对dept_code,operator_id列创建索引
 * 如果要使用数据权限,则需要在指定的方法上添加该注解(service层),同时在相应mapper的sql中拼接#{dataScope}即可
 * @see DataScopeAspect
 * @see DataScopeInterceptor
 * @author 殿小二
 * @date 2020/8/14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 涉及数据行的表别名
     * @return 例如: select * from data t where t.dept_code = x  则该别名为 t
     */
    String tableAlias() default "";
}
