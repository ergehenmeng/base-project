package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.application.shared.dto.sys.log.ManageQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.po.ManageLogPO;
import com.eghm.application.shared.vo.operate.log.ManageLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface ManageLogMapper extends BaseMapper<ManageLogPO> {

    /**
     * 根据条件查询操作日志
     *
     * @param page    分页参数
     * @param request 查询条件
     * @return 列表
     */
    Page<ManageLogResponse> getByPage(Page<ManageLogResponse> page, @Param("param") ManageQueryRequest request);

}
