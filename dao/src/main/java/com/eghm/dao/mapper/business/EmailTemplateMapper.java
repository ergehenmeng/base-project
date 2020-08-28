package com.eghm.dao.mapper.business;

import com.eghm.dao.model.business.EmailTemplate;

/**
 * @author 二哥很猛
 */
public interface EmailTemplateMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(EmailTemplate record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    EmailTemplate selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(EmailTemplate record);

}