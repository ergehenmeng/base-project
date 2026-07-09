package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysAreaPO;
import com.eghm.application.shared.vo.sys.ext.SysAreaVO;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysAreaMapper extends BaseMapper<SysAreaPO> {

    /**
     * 获取地区列表
     *
     * @param gradeList 分类
     * @return 列表
     */
    List<SysAreaVO> getList(List<Integer> gradeList);
}
