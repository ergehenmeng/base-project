package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.SysArea;
import com.eghm.vo.sys.SysAreaVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysAreaMapper extends BaseMapper<SysArea> {

    /**
     * 获取地区列表
     *
     * @param gradeList 分类
     * @return 列表
     */
    List<SysAreaVO> getList(List<Integer> gradeList);

    /**
     * 根据父级id获取地区列表
     *
     * @param pid pid
     * @return list
     */
    List<SysAreaVO> getByPid(@Param("pid") Long pid);
}