package com.eghm.service.common.impl;

import com.eghm.dao.mapper.system.TagViewMapper;
import com.eghm.dao.model.system.TagView;
import com.eghm.service.common.TagViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/11/27 10:22
 */
@Service("tagViewService")
@Transactional(rollbackFor = RuntimeException.class)
public class TagViewServiceImpl implements TagViewService {

    @Autowired
    private TagViewMapper tagViewMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<TagView> getList() {
        return tagViewMapper.getList();
    }
}
