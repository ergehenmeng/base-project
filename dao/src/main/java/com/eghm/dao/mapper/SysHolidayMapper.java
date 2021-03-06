package com.eghm.dao.mapper;

import com.eghm.dao.model.SysHoliday;

public interface SysHolidayMapper {


    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(SysHoliday record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    SysHoliday selectByPrimaryKey(Long id);
}