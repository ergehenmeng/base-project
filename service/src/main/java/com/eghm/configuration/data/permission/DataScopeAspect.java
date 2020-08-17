package com.eghm.configuration.data.permission;


import cn.hutool.core.util.ArrayUtil;
import com.eghm.common.enums.PermissionType;
import com.eghm.configuration.security.SecurityOperator;
import com.eghm.configuration.security.SecurityOperatorHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/8/14
 */
@Aspect
@Component
public class DataScopeAspect {

    private static final ThreadLocal<String> DATA_SCOPE_PARAM = new ThreadLocal<>();

    /**
     * 增强逻辑
     */
    @Around("@annotation(scope) && within(com.eghm.service..*)")
    public Object around(ProceedingJoinPoint joinPoint, DataScope scope) throws Throwable{
        try {
            SecurityOperator operator = SecurityOperatorHolder.getRequiredOperator();
            String sql = this.createPermissionSql(operator, scope);
            DATA_SCOPE_PARAM.set(sql);
            return joinPoint.proceed();
        } finally {
            DATA_SCOPE_PARAM.remove();
        }
    }

    /**
     * 根据用户数据权限生成额外的sql
     * @param operator 用户信息
     * @param scope 注解标示
     * @return sql 例如 t.dept_id = 123123
     */
    private String createPermissionSql(SecurityOperator operator, DataScope scope) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ( ");
        // 全部
        if (operator.getPermissionType() == PermissionType.ALL.getValue()) {
            builder.append(" 1 = 1");
        }
        // 自定义
        if (operator.getPermissionType() == PermissionType.CUSTOM.getValue()) {
            List<Integer> deptList = operator.getDeptList();
            builder.append(scope.tableAlias()).append(".dept_id in ( ").append(ArrayUtil.join(deptList.toArray(), ",")).append(" ) ");
        }
        // 本部门及子部门
        if (operator.getPermissionType() == PermissionType.DEPT.getValue()) {
            builder.append(scope.tableAlias()).append(".dept_id like '").append(operator.getDeptCode()).append("%' ");
        }
        // 本部门
        if (operator.getPermissionType() == PermissionType.SELF_DEPT.getValue()) {
            builder.append(scope.tableAlias()).append(".dept_id = '").append(operator.getDeptCode()).append("' ");
        }
        // 自己,可能会涉及到部门变更,默认老部门信息无法查看,因此此处过滤部门信息
        if (operator.getPermissionType() == PermissionType.SELF.getValue()) {
            builder.append(scope.tableAlias())
                    .append(".dept_id = '").append(operator.getDeptCode()).append("' and ")
                    .append(scope.tableAlias()).append(".operator_id = ").append(operator.getId());
        }
        builder.append(" ) ");
        return builder.toString();
    }

    /**
     * 获取数据权限sql
     * @return sql
     */
    public static String getSql() {
        return DATA_SCOPE_PARAM.get();
    }

}
