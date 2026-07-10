package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.auth.AuthConfigAddRequest;
import com.eghm.application.shared.dto.operate.auth.AuthConfigEditRequest;

import java.io.File;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
public interface AuthConfigApplicationService {

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

    /**
     * 重置秘钥
     *
     * @param id id
     */
    void reset(Long id);

    /**
     * 发送邮件
     *
     * @param id id
     * @param file 文档
     */
    void sendEmail(Long id, File file);
}
