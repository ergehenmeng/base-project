package com.eghm.integration.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.integration.storage.dto.ImageQueryRequest;
import com.eghm.integration.storage.entity.ImageLog;
import com.eghm.integration.storage.vo.ImageLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface ImageLogMapper extends BaseMapper<ImageLog> {

    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param request 查询参数
     * @return 列表
     */
    Page<ImageLogResponse> getByPage(Page<ImageLogResponse> page, @Param("param") ImageQueryRequest request);
}