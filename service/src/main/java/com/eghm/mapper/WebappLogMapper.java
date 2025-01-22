package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sys.log.WebappQueryRequest;
import com.eghm.model.WebappLog;
import com.eghm.vo.log.WebappLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author eghm
 */
public interface WebappLogMapper extends BaseMapper<WebappLog> {

    /**
     * 查询移动端操作记录
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<WebappLogResponse> getByPage(Page<WebappLogResponse> page, @Param("param") WebappQueryRequest request);
}