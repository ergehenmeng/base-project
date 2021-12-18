package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.AppletPayConfig;
import org.apache.ibatis.annotations.Param;

public interface AppletPayConfigMapper extends BaseMapper<AppletPayConfig> {

    /**
     * 根据nid查询支付配置消息
     * @param nid nid
     * @return
     */
    AppletPayConfig getByNid(@Param("nid") String nid);
}