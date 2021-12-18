package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SysConfig;
import com.eghm.model.dto.config.ConfigEditRequest;
import com.eghm.model.dto.config.ConfigQueryRequest;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 根据条件查询系统参数列表:参数类型,锁定状态,nid,备注信息
     * @param request 查询条件
     * @return
     */
    List<SysConfig> getList(ConfigQueryRequest request);

    /**
     * 根据nid获取系统参数
     * @param nid
     * @return
     */
    SysConfig getByNid(String nid);

    /**
     * 更新系统参数
     * @param request 待更新参数
     * @return
     */
    int updateConfig(ConfigEditRequest request);
}