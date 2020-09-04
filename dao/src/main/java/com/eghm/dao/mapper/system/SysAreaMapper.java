package com.eghm.dao.mapper.system;

import com.eghm.dao.model.system.SysArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysAreaMapper {
    /**
     * 插入不为空的记录
     * @param record 待插入对象
     * @return 插入条数 1
     */
    int insertSelective(SysArea record);

    /**
     * 根据主键获取一条数据库记录
     * @param id 主键
     * @return 一条数据
     */
    SysArea selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     * @param record 更新对象
     * @return 一条
     */
    int updateByPrimaryKeySelective(SysArea record);

    /**
     * 查询所有
     * @return 列表
     */
    List<SysArea> getList();

    /**
     * 根据pid查询地址列表
     * @param pid pid
     * @return list
     */
    List<SysArea> getByPid(@Param("pid")Integer pid);

}