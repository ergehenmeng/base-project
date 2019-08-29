package com.fanyin.dao.mapper.business;

import com.fanyin.dao.model.business.PushTemplate;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface PushTemplateMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(PushTemplate record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    PushTemplate selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(PushTemplate record);

    /**
     * 根据nid查询消息推送消息模板
     * @param nid nid
     * @return 消息模板
     */
    PushTemplate getByNid(@Param("nid")String nid);
}