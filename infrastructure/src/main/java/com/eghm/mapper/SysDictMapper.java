package com.eghm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dto.sys.dict.DictQueryRequest;
import com.eghm.po.SysDictPO;
import com.eghm.vo.sys.dict.DictResponse;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysDictMapper extends BaseMapper<SysDictPO> {

    /**
     * 查询列表
     *
     * @param request 查询条件
     * @return 列表
     */
    List<DictResponse> getList(DictQueryRequest request);

}
