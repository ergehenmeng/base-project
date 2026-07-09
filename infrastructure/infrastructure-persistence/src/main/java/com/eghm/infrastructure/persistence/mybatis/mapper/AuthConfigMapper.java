package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.infrastructure.persistence.mybatis.po.AuthConfigPO;
import com.eghm.application.shared.vo.operate.auth.AuthConfigResponse;
import com.eghm.application.shared.vo.operate.auth.AuthConfigVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
public interface AuthConfigMapper extends BaseMapper<AuthConfigPO> {

    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param queryName 查询参数
     * @return 分页结果
     */
    Page<AuthConfigResponse> getByPage(Page<AuthConfigResponse> page, @Param("queryName") String queryName);

    /**
     * 根据appId查询配置信息
     *
     * @param appId appId
     * @return 配置信息
     */
    AuthConfigVO getByAppId(@Param("appId") String appId);
}

