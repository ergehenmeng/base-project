package com.eghm.service.sys;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.SysConfig;
import com.eghm.model.dto.config.ConfigEditRequest;
import com.eghm.model.dto.config.ConfigQueryRequest;


/**
 * @author 二哥很猛
 * @date 2018/1/12 09:45
 */
public interface SysConfigService {

    /**
     * 更新系统参数
     * @param request 待更新的参数对象
     */
    void update(ConfigEditRequest request);

    /**
     * 分页查询系统配置信息
     * @param request 查询条件
     * @return 分页结果集
     */
    Page<SysConfig> getByPage(ConfigQueryRequest request);

    /**
     * 根据nid获取系统配置信息
     * @param nid 唯一nid
     * @return 参数配置独享
     */
    String getByNid(String nid);

    /**
     * 根据主键获取系统参数
     * @param id 主键
     * @return 系统参数信息
     */
    SysConfig getById(Long id);

}
