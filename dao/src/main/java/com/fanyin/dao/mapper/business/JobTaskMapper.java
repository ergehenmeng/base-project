package com.fanyin.dao.mapper.business;

import com.fanyin.dao.model.business.JobTask;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface JobTaskMapper {

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    JobTask selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(JobTask record);

    /**
     * 获取所有开启的定时任务
     * @return 定时任务配置列表
     */
    List<JobTask> getAvailableList();

}