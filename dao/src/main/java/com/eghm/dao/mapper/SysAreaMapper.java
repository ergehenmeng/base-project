package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SysArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysAreaMapper extends BaseMapper<SysArea> {

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
    List<SysArea> getByPid(@Param("pid")Long pid);

}