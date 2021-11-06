package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.NoticeTemplate;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface NoticeTemplateMapper extends BaseMapper<NoticeTemplate> {

    /**
     * 模板查询
     * @param code code
     * @return template
     */
    NoticeTemplate getTemplate(@Param("code") String code);
}