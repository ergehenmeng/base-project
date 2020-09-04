package com.eghm.service.sys;

import com.eghm.model.vo.sys.SysAreaVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/2/13 10:25
 */
public interface SysAreaService {

    /**
     * 计算首字母
     */
    void calcInitial();

    /**
     * 查询子级的地址列表
     * @param pid 当前级地址id
     * @return list
     */
    List<SysAreaVO> getByPid(Integer pid);
}

