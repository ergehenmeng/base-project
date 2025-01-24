package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sys.task.TaskLogQueryRequest;
import com.eghm.model.SysTaskLog;
import com.eghm.vo.operate.log.SysTaskLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SysTaskLogMapper extends BaseMapper<SysTaskLog> {

    /**
     * 分页查询
     *
     * @param page    分页参数
     * @param request 查询参数
     * @return 分页结果
     */
    Page<SysTaskLogResponse> getByPage(Page<SysTaskLogResponse> page, @Param("param") TaskLogQueryRequest request);

    /**
     * 定时任务错误信息详情
     *
     * @param id 主键
     * @return errorMsg字段有值
     */
    String getErrorMsg(@Param("id") Long id);
}