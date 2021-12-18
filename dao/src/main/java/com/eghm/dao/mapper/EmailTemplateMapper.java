package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.EmailTemplate;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface EmailTemplateMapper extends BaseMapper<EmailTemplate> {

    /**
     * 根据nid查找邮件模板信息
     * @param nid nid
     * @return template
     */
    EmailTemplate getByNid(@Param("nid") String nid);

}