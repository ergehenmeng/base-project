package com.eghm.dao.mapper;

import com.eghm.dao.model.NoticeTemplate;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface NoticeTemplateMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(NoticeTemplate record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    NoticeTemplate selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(NoticeTemplate record);

    /**
     * 模板查询
     * @param code code
     * @return template
     */
    NoticeTemplate getTemplate(@Param("code") String code);
}