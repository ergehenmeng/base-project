package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.push.PushTemplateQueryRequest;
import com.eghm.model.PushTemplate;
import com.eghm.vo.push.PushTemplateResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface PushTemplateMapper extends BaseMapper<PushTemplate> {

    /**
     * 分页查询
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<PushTemplateResponse> getByPage(Page<PushTemplateResponse> page, @Param("param") PushTemplateQueryRequest request);
}