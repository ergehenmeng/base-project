package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.BlackRoster;
import com.eghm.model.dto.roster.BlackRosterQueryRequest;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface BlackRosterMapper extends BaseMapper<BlackRoster> {

    /**
     * 根据条件查询黑名单列表
     * @param request 查询条件
     * @return 列表
     */
    List<BlackRoster> getList(BlackRosterQueryRequest request);

    /**
     * 黑名单列表
     * @return 列表
     */
    List<BlackRoster> getAvailableList();
}