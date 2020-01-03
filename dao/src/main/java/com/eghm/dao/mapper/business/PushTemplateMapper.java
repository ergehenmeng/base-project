package com.eghm.dao.mapper.business;

import com.eghm.dao.model.business.PushTemplate;
import com.eghm.model.dto.business.push.PushTemplateQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface PushTemplateMapper {

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

    /**
     * 根据条件查询消息模板
     * @param request 查询条件
     * @return 推送模板
     */
    List<PushTemplate> getList(PushTemplateQueryRequest request);
}