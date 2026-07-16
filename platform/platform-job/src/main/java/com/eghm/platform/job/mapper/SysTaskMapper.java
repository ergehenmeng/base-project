package com.eghm.platform.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.platform.job.dto.TaskQueryRequest;
import com.eghm.platform.job.entity.SysTask;
import com.eghm.platform.job.vo.SysTaskResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SysTaskMapper extends BaseMapper<SysTask> {

    /**
     * 分页查询
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<SysTaskResponse> getByPage(Page<SysTaskResponse> page, @Param("param") TaskQueryRequest request);

}