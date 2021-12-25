package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.BlackRoster;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface BlackRosterMapper extends BaseMapper<BlackRoster> {

    /**
     * 黑名单列表
     *
     * @return 列表
     */
    List<BlackRoster> getAvailableList();
}