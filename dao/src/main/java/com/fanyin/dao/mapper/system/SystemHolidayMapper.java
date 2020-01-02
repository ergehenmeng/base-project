package com.fanyin.dao.mapper.system;

import com.fanyin.dao.model.system.SystemHoliday;

public interface SystemHolidayMapper {


    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(SystemHoliday record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    SystemHoliday selectByPrimaryKey(Integer id);
}