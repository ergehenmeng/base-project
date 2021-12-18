package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SmsTemplate;
import com.eghm.model.dto.sms.SmsTemplateQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据条件查询短信模板列表
     *
     * @param request 查询条件
     * @return 列表
     */
    List<SmsTemplate> getList(SmsTemplateQueryRequest request);
}