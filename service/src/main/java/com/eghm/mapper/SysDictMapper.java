package com.eghm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.dict.DictQueryRequest;
import com.eghm.model.SysDict;
import com.eghm.vo.sys.DictResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 分页查询
     * @param page 分页对象
     * @param request 查询条件
     * @return 列表
     */
    Page<DictResponse> getByPage(Page<DictResponse> page, @Param("param") DictQueryRequest request);
}