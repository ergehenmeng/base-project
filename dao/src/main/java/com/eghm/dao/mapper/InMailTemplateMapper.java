package com.eghm.dao.mapper;

import com.eghm.dao.model.InMailTemplate;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface InMailTemplateMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(InMailTemplate record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    InMailTemplate selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(InMailTemplate record);

    /**
     * 模板查询
     * @param code code
     * @return template
     */
    InMailTemplate getTemplate(@Param("code") String code);
}