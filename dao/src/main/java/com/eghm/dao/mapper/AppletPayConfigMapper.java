package com.eghm.dao.mapper;

import com.eghm.dao.model.AppletPayConfig;
import org.apache.ibatis.annotations.Param;

public interface AppletPayConfigMapper {
    /**
     * 插入数据库记录
     *
     * @param record 条件
     */
    int insert(AppletPayConfig record);

    /**
     * 插入不为空的记录
     *
     * @param record 条件
     */
    int insertSelective(AppletPayConfig record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件
     */
    AppletPayConfig selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件
     */
    int updateByPrimaryKeySelective(AppletPayConfig record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record 条件
     */
    int updateByPrimaryKey(AppletPayConfig record);

    /**
     * 根据nid查询支付配置消息
     * @param nid nid
     * @return
     */
    AppletPayConfig getByNid(@Param("nid") String nid);
}