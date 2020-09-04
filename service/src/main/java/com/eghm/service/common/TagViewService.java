package com.eghm.service.common;

import com.eghm.dao.model.TagView;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/11/27 10:22
 */
public interface TagViewService {

    /**
     * 获取所有的view标签信息
     * @return 标签view映射关系
     */
    List<TagView> getList();
}
