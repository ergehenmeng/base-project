package com.fanyin.dao.mapper.business;

import com.fanyin.dao.model.business.MessageTemplate;

/**
 * @author 二哥很猛
 */
public interface MessageTemplateMapper {

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    MessageTemplate selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(MessageTemplate record);

}