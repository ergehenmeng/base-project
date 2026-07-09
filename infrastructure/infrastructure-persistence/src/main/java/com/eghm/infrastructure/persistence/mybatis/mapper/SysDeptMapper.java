package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysDeptPO;
import com.eghm.vo.sys.ext.SysDeptResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysDeptMapper extends BaseMapper<SysDeptPO> {

    /**
     * 获取部门下的子级部门最大编号的部门
     *
     * @param code 部门编号
     * @return 子部门列表
     */
    String getMaxCodeChild(@Param("code") String code);

    /**
     * 获取部门列表
     *
     * @param queryName 查询条件
     * @return 列表
     */
    List<SysDeptResponse> getList(@Param("queryName") String queryName);
}
