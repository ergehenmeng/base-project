package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.auth.AuthConfigQueryRequest;
import com.eghm.model.AuthConfig;
import com.eghm.vo.operate.auth.AuthConfigResponse;
import com.eghm.vo.operate.auth.AuthConfigVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
public interface AuthConfigMapper extends BaseMapper<AuthConfig> {

    /**
     * 分页查询
     *
     * @param page     分页对象
     * @param request   查询参数
     * @return 分页结果
     */
    Page<AuthConfigResponse> getByPage(Page<AuthConfigResponse> page, @Param("param") AuthConfigQueryRequest request);

    /**
     * 根据appKey查询配置信息
     *
     * @param appKey appKey
     * @return 配置信息
     */
    AuthConfigVO getByAppKey(@Param("appKey") String appKey);
}
