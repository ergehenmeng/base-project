package com.fanyin.mapper.system;


import com.fanyin.dto.system.operator.OperatorQueryRequest;
import com.fanyin.model.system.SystemOperator;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SystemOperatorMapper {
    /**
     * 插入不为空的记录
     *
     * @param record
     * @return
     */
    int insertSelective(SystemOperator record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     * @return
     */
    SystemOperator selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SystemOperator record);

    /**
     * 根据手机号码查询管理员信息
     * @param mobile 手机号码
     * @return
     */
    SystemOperator getByMobile(String mobile);

    /**
     * 根据条件查询系统人员信息
     * @param request 查询条件
     * @return 列表
     */
    List<SystemOperator> getList(OperatorQueryRequest request);
}