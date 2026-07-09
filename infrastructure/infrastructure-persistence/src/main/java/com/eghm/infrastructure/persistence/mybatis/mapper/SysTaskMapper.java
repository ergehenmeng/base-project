package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.application.shared.dto.sys.task.TaskQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.po.SysTaskPO;
import com.eghm.application.shared.vo.operate.task.SysTaskResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SysTaskMapper extends BaseMapper<SysTaskPO> {

    /**
     * 分页查询
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<SysTaskResponse> getByPage(Page<SysTaskResponse> page, @Param("param") TaskQueryRequest request);

}
