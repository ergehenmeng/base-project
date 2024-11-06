package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.task.TaskQueryRequest;
import com.eghm.model.SysTask;
import com.eghm.vo.task.SysTaskResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SysTaskMapper extends BaseMapper<SysTask> {

    /**
     * 分页查询
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<SysTaskResponse> getByPage(Page<SysTaskResponse> page, @Param("param") TaskQueryRequest request);

}