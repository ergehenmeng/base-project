package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.PushTemplate;
import com.eghm.model.dto.push.PushTemplateQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface PushTemplateMapper extends BaseMapper<PushTemplate> {

    /**
     * 根据nid查询消息推送消息模板
     *
     * @param nid nid
     * @return 消息模板
     */
    PushTemplate getByNid(@Param("nid") String nid);

    /**
     * 根据条件查询消息模板
     *
     * @param request 查询条件
     * @return 推送模板
     */
    List<PushTemplate> getList(PushTemplateQueryRequest request);
}