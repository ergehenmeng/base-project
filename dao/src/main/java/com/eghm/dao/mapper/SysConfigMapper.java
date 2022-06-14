package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SysConfig;
import com.eghm.model.dto.config.ConfigEditRequest;

/**
 * @author 二哥很猛
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 根据nid获取系统参数
     * @param nid nid
     * @return 配置信息
     */
    SysConfig getByNid(String nid);

    /**
     * 更新系统参数
     * @param request 待更新参数
     * @return 1
     */
    int updateConfig(ConfigEditRequest request);
}