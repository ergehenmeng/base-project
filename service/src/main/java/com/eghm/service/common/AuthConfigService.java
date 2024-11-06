package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.auth.AuthConfigAddRequest;
import com.eghm.dto.operate.auth.AuthConfigEditRequest;
import com.eghm.dto.operate.auth.AuthConfigQueryRequest;
import com.eghm.vo.auth.AuthConfigResponse;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
public interface AuthConfigService {

    /**
     * 分页查询第三方配置信息
     *
     * @param request 查询条件
     * @return 分页列表
     */
    Page<AuthConfigResponse> getByPage(AuthConfigQueryRequest request);

    /**
     * 创建第三方授权配置信息,并生成秘钥
     *
     * @param request 第三方信息
     */
    void create(AuthConfigAddRequest request);

    /**
     * 编辑第三方授权配置信息
     *
     * @param request 第三方信息
     */
    void update(AuthConfigEditRequest request);

    /**
     * 删除授权信息
     *
     * @param id id
     */
    void deleteById(Long id);
}
