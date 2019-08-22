package com.fanyin.dao.mapper.business;

import com.fanyin.dao.model.business.AppVersion;
import com.fanyin.model.dto.business.version.VersionQueryRequest;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface AppVersionMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(AppVersion record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    AppVersion selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(AppVersion record);

    /**
     * 根据条件查询app管理列表
     * @param request 查询条件
     * @return 列表
     */
    List<AppVersion> getList(VersionQueryRequest request);

}