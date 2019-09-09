package com.fanyin.dao.mapper.system;

import com.fanyin.dao.model.system.BlackRoster;

/**
 * @author 二哥很猛
 */
public interface BlackRosterMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(BlackRoster record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    BlackRoster selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(BlackRoster record);

}