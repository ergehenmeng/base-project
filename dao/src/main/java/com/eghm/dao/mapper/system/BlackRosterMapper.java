package com.eghm.dao.mapper.system;

import com.eghm.dao.model.system.BlackRoster;
import com.eghm.model.dto.system.roster.BlackRosterQueryRequest;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface BlackRosterMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(BlackRoster record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    BlackRoster selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(BlackRoster record);


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