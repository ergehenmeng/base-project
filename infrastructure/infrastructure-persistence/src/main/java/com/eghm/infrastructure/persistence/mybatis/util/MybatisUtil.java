package com.eghm.infrastructure.persistence.mybatis.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.eghm.constants.CommonConstant;
import lombok.Data;

import java.util.List;

/**
 * @author wyb-eghm
 * @since 2026/6/1
 */
@Data
public class MybatisUtil {
    
    /**
     * 根据字段值查询单条数据
     *
     * @param baseMapper mapper
     * @param fieldGetter 字段查询器
     * @param value 字段值
     * @param <T> 实体类
     * @return T
     */
    public static <T> T getOne(BaseMapper<T> baseMapper, SFunction<T, ?> fieldGetter, Object value) {
        return baseMapper.selectOne(new LambdaQueryWrapper<T>().eq(fieldGetter, value).last(CommonConstant.LIMIT_ONE));
    }
    
    /**
     * 根据字段值查询单条数据
     *
     * @param baseMapper mapper
     * @param fieldGetter 字段查询器
     * @param value 字段值
     * @param <T> 实体类
     * @return T
     */
    public static <T> List<T> getList(BaseMapper<T> baseMapper, SFunction<T, ?> fieldGetter, Object value) {
        return baseMapper.selectList(new LambdaQueryWrapper<T>().eq(fieldGetter, value));
    }
    
}
