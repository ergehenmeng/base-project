package com.eghm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dto.dict.DictQueryRequest;
import com.eghm.model.SysDict;
import com.eghm.vo.sys.DictResponse;

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