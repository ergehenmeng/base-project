package com.eghm.infrastructure.persistence.mybatis.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * 通用参数校验工具类
 * 提供重复性校验等通用校验方法
 *
 * @author 二哥很猛
 * @since 2025/1/28
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUtil {

    /**
     * 校验字段值是否重复（支持附加条件）
     *
     * @param mapper      MyBatis-Plus Mapper
     * @param fieldGetter 字段获取函数
     * @param value       待校验的值
     * @param idGetter    ID获取函数
     * @param excludeId   需要排除的ID（更新时使用，可为null）
     * @param errorCode   重复时的错误码
     * @param logMessage  重复时的日志消息模板（支持 {value} 和 {excludeId} 占位符）
     * @param <T>         实体类型
     * @param <ID>        ID类型
     */
    public static <T, ID> void redoCheck(BaseMapper<T> mapper, SFunction<T, ?> fieldGetter, Object value, SFunction<T, ID> idGetter, ID excludeId, ErrorCode errorCode, String logMessage) {
        redoCheck(mapper, fieldGetter, value, null, idGetter, excludeId, errorCode, logMessage);
    }

    /**
     * 校验字段值是否重复（支持多条件）
     *
     * @param mapper        MyBatis-Plus Mapper
     * @param fieldGetter   主字段获取函数
     * @param value         待校验的值
     * @param condition     额外的查询条件构建器
     * @param excludeId     需要排除的ID（更新时使用，可为null）
     * @param idGetter      ID获取函数
     * @param errorCode     重复时的错误码
     * @param logMessage    重复时的日志消息模板
     * @param <T>           实体类型
     * @param <ID>          ID类型
     */
    public static <T, ID> void redoCheck(BaseMapper<T> mapper, SFunction<T, ?> fieldGetter, Object value, Consumer<LambdaQueryWrapper<T>> condition,
            SFunction<T, ID> idGetter, ID excludeId, ErrorCode errorCode, String logMessage) {
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(fieldGetter, value);
        if (condition != null) {
            condition.accept(wrapper);
        }
        if (excludeId != null && idGetter != null) {
            wrapper.ne(idGetter, excludeId);
        }
        Long count = mapper.selectCount(wrapper);
        if (count > 0) {
            if (logMessage != null) {
                log.warn(logMessage, value, excludeId);
            }
            throw new BusinessException(errorCode);
        }
    }

}
