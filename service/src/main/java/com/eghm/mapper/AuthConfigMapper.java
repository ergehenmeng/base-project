package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.AuthConfig;
import com.eghm.vo.auth.AuthConfigVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
public interface AuthConfigMapper extends BaseMapper<AuthConfig> {

    /**
     * 根据appKey查询配置信息
     *
     * @param appKey appKey
     * @return 配置信息
     */
    AuthConfigVO getByAppKey(@Param("appKey") String appKey);
}
