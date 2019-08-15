package com.fanyin.mapper.system;

import com.fanyin.model.system.SystemAddress;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SystemAddressMapper {
    /**
     * 插入不为空的记录
     * @param record 待插入对象
     * @return 插入条数 1
     */
    int insertSelective(SystemAddress record);

    /**
     * 根据主键获取一条数据库记录
     * @param id 主键
     * @return 一条数据
     */
    SystemAddress selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     * @param record 更新对象
     * @return 一条
     */
    int updateByPrimaryKeySelective(SystemAddress record);

    /**
     * 查询所有
     * @return 列表
     */
    List<SystemAddress> getList();

}