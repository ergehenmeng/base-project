package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SysDept;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 获取部门下的子级部门最大编号的部门
     *
     * @param code 部门编号
     * @return 子部门列表
     */
    SysDept getMaxCodeChild(@Param("code") String code);

}