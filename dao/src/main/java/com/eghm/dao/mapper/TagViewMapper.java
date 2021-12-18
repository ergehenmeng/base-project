package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.TagView;

import java.util.List;

public interface TagViewMapper extends BaseMapper<TagView> {

    /**
     * 获取所有的view标签信息
     * @return 列表
     */
    List<TagView> getList();

}