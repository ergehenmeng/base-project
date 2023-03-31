package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.ManageLog;
import com.eghm.dto.log.ManageQueryRequest;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface ManageLogMapper extends BaseMapper<ManageLog> {

    /**
     * 根据条件查询操作日志
     * @param page    分页参数
     * @param request 查询条件
     * @return 列表
     */
    Page<ManageLog> getByPage(Page<ManageLog> page, @Param("param") ManageQueryRequest request);

    /**
     * 根据id查询响应信息
     * @param id 主键
     * @return 响应结果,可能为空
     */
    String getResponseById(@Param("id") Long id);
}