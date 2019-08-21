package com.fanyin.dao.mapper.system;

import com.fanyin.dao.model.system.SmsTemplate;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface SmsTemplateMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(SmsTemplate record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    SmsTemplate selectByPrimaryKey(Integer id);

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

}