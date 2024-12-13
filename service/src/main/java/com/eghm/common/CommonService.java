package com.eghm.common;

import com.eghm.vo.sys.ext.SysAreaVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
public interface CommonService {

    /**
     * 查询地址列表(树状结构)
     *
     * @return list
     */
    List<SysAreaVO> getTreeAreaList();

    /**
     * 查询地址列表(树状结构)
     *
     * @param gradeList 省份1级 市2级 县3级
     * @return list
     */
    List<SysAreaVO> getTreeAreaList(List<Integer> gradeList);
}
