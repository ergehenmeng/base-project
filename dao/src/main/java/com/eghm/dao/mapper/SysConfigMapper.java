package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SysConfig;

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

}