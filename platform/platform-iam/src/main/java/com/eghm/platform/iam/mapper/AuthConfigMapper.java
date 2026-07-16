package com.eghm.platform.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.platform.iam.entity.AuthConfig;
import com.eghm.platform.iam.vo.AuthConfigResponse;
import com.eghm.platform.iam.vo.AuthConfigVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
public interface AuthConfigMapper extends BaseMapper<AuthConfig> {

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
