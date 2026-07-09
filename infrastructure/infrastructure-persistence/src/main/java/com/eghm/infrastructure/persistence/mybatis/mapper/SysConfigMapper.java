package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sys.config.ConfigQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.po.SysConfigPO;
import com.eghm.vo.sys.ext.SysConfigResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SysConfigMapper extends BaseMapper<SysConfigPO> {

    /**
     * 分页查询
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<SysConfigResponse> getByPage(Page<SysConfigResponse> page, @Param("param") ConfigQueryRequest request);
}
