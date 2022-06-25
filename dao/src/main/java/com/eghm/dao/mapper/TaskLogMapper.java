package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.TaskLog;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface TaskLogMapper extends BaseMapper<TaskLog> {

    /**
     * 定时任务错误信息详情
     * @param id 主键
     * @return errorMsg字段有值
     */
    String getErrorMsg(@Param("id") Long id);
}