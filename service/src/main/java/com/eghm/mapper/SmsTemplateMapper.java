package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.SmsTemplate;
import com.eghm.vo.template.SmsTemplateResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SmsTemplateMapper extends BaseMapper<SmsTemplate> {

    /**
     * 分页查询
     *
     * @param page 分页信息
     * @param queryName 查询条件
     * @return 列表
     */
    Page<SmsTemplateResponse> getByPage(Page<SmsTemplateResponse> page, @Param("queryName") String queryName);

}