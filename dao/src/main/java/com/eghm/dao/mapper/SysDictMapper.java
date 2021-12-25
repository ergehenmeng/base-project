package com.eghm.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.dto.dict.DictQueryRequest;
import com.eghm.dao.model.SysDict;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 根据nid查询某一类数据字典列表
     * @param nid 某类数据字典nid
     * @return 查询到的列表
     */
    List<SysDict> getDictByNid(String nid);

    /**
     * 根据主键选择更新数据字典信息
     * @param dict 前台参数
     * @return 影响条数
     */
    int updateByIdSelective(SysDict dict);
}