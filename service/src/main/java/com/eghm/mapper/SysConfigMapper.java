package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.config.ConfigQueryRequest;
import com.eghm.model.SysConfig;
import com.eghm.vo.sys.SysConfigResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 分页查询
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<SysConfigResponse> getByPage(Page<SysConfigResponse> page, @Param("param") ConfigQueryRequest request);
}