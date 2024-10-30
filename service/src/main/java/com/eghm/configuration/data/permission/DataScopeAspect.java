package com.eghm.configuration.data.permission;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.DataType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.List;

/**
 * 生成数据权限拦截sql
 * 涉及数据权限的表中必须包含两个字段, 用户所属部门:dept_code, 用户ID:user_id
 * 在Mapper的方法上添加@DataScope注解
 * 在sql中可以通过${dataScope}直接注入数据权限部分的sql
 *
 * @author 殿小二
 * @since 2020/8/14
 * @see DataScopeInterceptor 拦截器
 */
@Aspect
public class DataScopeAspect {

    private static final ThreadLocal<String> DATA_SCOPE_PARAM = new ThreadLocal<>();

    /**
     * 获取数据权限sql
     *
     * @return sql
     */
    public static String getScope() {
        return DATA_SCOPE_PARAM.get();
    }

    /**
     * 增强逻辑
     */
    @Around("@annotation(scope) && this(com.baomidou.mybatisplus.core.mapper.BaseMapper)")
    public Object around(ProceedingJoinPoint joinPoint, DataScope scope) throws Throwable {
        try {
            UserToken user = SecurityHolder.getUserRequired();
            String sql = this.createPermissionSql(user, scope);
            DATA_SCOPE_PARAM.set(sql);
            return joinPoint.proceed();
        } finally {
            DATA_SCOPE_PARAM.remove();
        }
    }

    /**
     * 根据用户数据权限生成额外的sql
     *
     * @param user  用户信息
     * @param scope 注解标示
     * @return sql 例如 t.dept_id = 123123
     */
    private String createPermissionSql(UserToken user, DataScope scope) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ( ");
        String alias = StrUtil.isBlank(scope.alias()) ? "" : scope.alias().trim() + ".";
        // 自定义
        if (user.getDataType() == DataType.CUSTOM) {
            List<String> deptList = user.getDataList();
            if (CollUtil.isEmpty(deptList)) {
                builder.append(" 1 = 2 ");
            } else {
                builder.append(alias).append("dept_code in ( ").append(ArrayUtil.join(deptList.toArray(), ",")).append(" ) ");
            }
        }
        // 本部门及子部门
        if (user.getDataType() == DataType.DEPT) {
            builder.append(alias).append("dept_code like '").append(user.getDeptCode()).append("%' ");
        }
        // 本部门
        if (user.getDataType() == DataType.SELF_DEPT) {
            builder.append(alias).append("dept_code = '").append(user.getDeptCode()).append("' ");
        }
        // 自己,可能会涉及到部门变更, 默认老部门信息无法查看, 因此此处过滤部门信息
        if (user.getDataType() == DataType.SELF) {
            builder.append(alias)
                    .append("dept_code = '").append(user.getDeptCode()).append("' and ")
                    .append(alias).append("user_id = ").append(user.getId());
        }
        // 全部
        if (user.getDataType() == DataType.ALL) {
            builder.append(" 1 = 1 ");
        }
        builder.append(" ) ");
        return builder.toString();
    }

}
