package com.fanyin.dao.mapper.system;

import com.fanyin.model.dto.system.log.OperationQueryRequest;
import com.fanyin.dao.model.system.SystemOperationLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SystemOperationLogMapper {

    /**
     * 插入不为空的记录
     *
     * @param record
     */
    int insertSelective(SystemOperationLog record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SystemOperationLog selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SystemOperationLog record);

    /**
     * 根据条件查询操作日志
     * @param request 查询条件
     * @return 列表
     */
    List<SystemOperationLog> getList(OperationQueryRequest request);

    /**
     * 根据id查询响应信息
     * @param id 主键
     * @return 响应结果,可能为空
     */
    String getResponseById(@Param("id") Integer id);
}