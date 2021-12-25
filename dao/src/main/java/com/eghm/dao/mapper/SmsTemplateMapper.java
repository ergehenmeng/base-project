package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SmsTemplate;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SmsTemplateMapper extends BaseMapper<SmsTemplate> {

    /**
     * 根据nid查询短信模板信息
     *
     * @param nid nid
     * @return 短信模板信息
     */
    SmsTemplate getByNid(@Param("nid") String nid);

}