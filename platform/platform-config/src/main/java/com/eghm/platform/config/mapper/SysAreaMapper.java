package com.eghm.platform.config.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.platform.config.entity.SysArea;
import com.eghm.platform.config.vo.SysAreaVO;

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
}