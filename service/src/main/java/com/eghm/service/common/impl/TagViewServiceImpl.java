package com.eghm.service.common.impl;

import com.eghm.dao.mapper.TagViewMapper;
import com.eghm.dao.model.TagView;
import com.eghm.service.common.TagViewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/11/27 10:22
 */
@Service("tagViewService")
@AllArgsConstructor
public class TagViewServiceImpl implements TagViewService {

    private final TagViewMapper tagViewMapper;

    @Override
    public List<TagView> getList() {
        return tagViewMapper.selectList(null);
    }
}
