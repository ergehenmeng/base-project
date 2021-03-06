package com.eghm.dao.mapper;

import com.eghm.dao.model.SmsTemplate;
import com.eghm.model.dto.sms.SmsTemplateQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SmsTemplateMapper {

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    SmsTemplate selectByPrimaryKey(Long id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(SmsTemplate record);

    /**
     * 根据nid查询短信模板信息
     * @param nid nid
     * @return 短信模板信息
     */
    SmsTemplate getByNid(@Param("nid")String nid);

    /**
     * 根据条件查询短信模板列表
     * @param request 查询条件
     * @return 列表
     */
    List<SmsTemplate> getList(SmsTemplateQueryRequest request);
}