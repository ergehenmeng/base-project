package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.log.ManageQueryRequest;
import com.eghm.model.ManageLog;
import com.eghm.vo.log.ManageLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface ManageLogMapper extends BaseMapper<ManageLog> {

    /**
     * 根据条件查询操作日志
     *
     * @param page    分页参数
     * @param request 查询条件
     * @return 列表
     */
    Page<ManageLogResponse> getByPage(Page<ManageLog> page, @Param("param") ManageQueryRequest request);

}