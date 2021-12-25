package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.PushTemplate;
import org.apache.ibatis.annotations.Param;

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

}