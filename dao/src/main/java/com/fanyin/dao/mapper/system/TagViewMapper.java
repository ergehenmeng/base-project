package com.fanyin.dao.mapper.system;

import com.fanyin.dao.model.system.TagView;

import java.util.List;

public interface TagViewMapper {

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    TagView selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(TagView record);

    /**
     * 获取所有的view标签信息
     * @return 列表
     */
    List<TagView> getList();

}