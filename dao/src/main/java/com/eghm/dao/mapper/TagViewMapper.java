package com.eghm.dao.mapper;

import com.eghm.dao.model.TagView;

import java.util.List;

public interface TagViewMapper {

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    TagView selectByPrimaryKey(Long id);

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