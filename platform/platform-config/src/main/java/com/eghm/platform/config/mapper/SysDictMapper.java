package com.eghm.platform.config.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.platform.config.dto.DictQueryRequest;
import com.eghm.platform.config.entity.SysDict;
import com.eghm.platform.config.vo.DictResponse;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 查询列表
     *
     * @param request 查询条件
     * @return 列表
     */
    List<DictResponse> getList(DictQueryRequest request);

}