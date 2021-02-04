package com.eghm.dao.mapper;


import com.eghm.model.dto.operator.OperatorQueryRequest;
import com.eghm.dao.model.SysOperator;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysOperatorMapper {
    /**
     * 插入不为空的记录
     *
     * @param record
     * @return
     */
    int insertSelective(SysOperator record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     * @return
     */
    SysOperator selectByPrimaryKey(Long id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SysOperator record);

    /**
     * 根据手机号码查询管理员信息
     * @param mobile 手机号码
     * @return
     */
    SysOperator getByMobile(String mobile);

    /**
     * 根据条件查询系统人员信息
     * @param request 查询条件
     * @return 列表
     */
    List<SysOperator> getList(OperatorQueryRequest request);
}