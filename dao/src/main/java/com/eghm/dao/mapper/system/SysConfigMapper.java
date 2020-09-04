package com.eghm.dao.mapper.system;

import com.eghm.model.dto.config.ConfigEditRequest;
import com.eghm.model.dto.config.ConfigQueryRequest;
import com.eghm.dao.model.system.SysConfig;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysConfigMapper {

    /**
     * 根据条件查询系统参数列表:参数类型,锁定状态,nid,备注信息
     * @param request 查询条件
     * @return
     */
    List<SysConfig> getList(ConfigQueryRequest request);


    /**
     * 插入不为空的记录
     *
     * @param record
     * @return
     */
    int insertSelective(SysConfig record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     * @return
     */
    SysConfig selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysConfig record);


    /**
     * 根据nid获取系统参数
     * @param nid
     * @return
     */
    SysConfig getByNid(String nid);

    /**
     * 更新系统参数
     * @param request 待更新参数
     * @return
     */
    int updateConfig(ConfigEditRequest request);
}