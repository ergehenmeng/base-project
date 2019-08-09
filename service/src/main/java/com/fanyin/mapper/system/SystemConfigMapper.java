package com.fanyin.mapper.system;

import com.fanyin.dto.system.config.ConfigEditRequest;
import com.fanyin.dto.system.config.ConfigQueryRequest;
import com.fanyin.model.system.SystemConfig;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SystemConfigMapper {

    /**
     * 根据条件查询系统参数列表:参数类型,锁定状态,nid,备注信息
     * @param request 查询条件
     * @return
     */
    List<SystemConfig> getList(ConfigQueryRequest request);


    /**
     * 插入不为空的记录
     *
     * @param record
     * @return
     */
    int insertSelective(SystemConfig record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     * @return
     */
    SystemConfig selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SystemConfig record);


    /**
     * 根据nid获取系统参数
     * @param nid
     * @return
     */
    SystemConfig getByNid(String nid);

    /**
     * 更新系统参数
     * @param request 待更新参数
     * @return
     */
    int updateConfig(ConfigEditRequest request);
}