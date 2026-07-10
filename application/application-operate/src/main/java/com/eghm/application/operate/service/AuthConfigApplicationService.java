package com.eghm.application.operate.service;

import cn.hutool.core.util.IdUtil;
import com.eghm.application.shared.common.EmailService;
import com.eghm.application.shared.dto.operate.auth.AuthConfigAddRequest;
import com.eghm.application.shared.dto.operate.auth.AuthConfigEditRequest;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.StringUtil;
import com.eghm.domain.operate.model.AuthConfig;
import com.eghm.domain.operate.repository.AuthConfigRepository;
import com.eghm.domain.operate.service.AuthConfigDomainService;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Service
@AllArgsConstructor
public class AuthConfigApplicationService {

    private final EmailService emailService;

    private final AuthConfigRepository authConfigRepository;

    private static final AuthConfigDomainService AUTH_CONFIG_DOMAIN_SERVICE = new AuthConfigDomainService();

    /**
     * 创建第三方授权配置信息,并生成秘钥
     *
     * @param request 第三方信息
     */
    public void create(AuthConfigAddRequest request) {
        AUTH_CONFIG_DOMAIN_SERVICE.assertTitleAvailable(authConfigRepository, request.getTitle(), null);
        AuthConfig config = DataUtil.copy(request, AuthConfig.class);
        config.initialize(IdUtil.fastSimpleUUID(), this.generateSecretKey(), LocalDate.now());
        authConfigRepository.save(config);
    }

    /**
     * 编辑第三方授权配置信息
     *
     * @param request 第三方信息
     */
    public void update(AuthConfigEditRequest request) {
        AUTH_CONFIG_DOMAIN_SERVICE.assertTitleAvailable(authConfigRepository, request.getTitle(), request.getId());
        AuthConfig config = DataUtil.copy(request, AuthConfig.class);
        authConfigRepository.update(config);
    }

    /**
     * 删除授权信息
     *
     * @param id id
     */
    public void deleteById(Long id) {
        authConfigRepository.deleteById(id);
    }

    /**
     * 重置秘钥
     *
     * @param id id
     */
    public void reset(Long id) {
        AuthConfig config = this.getByAuthConfigRequired(id);
        config.resetSecret(this.generateSecretKey());
        authConfigRepository.update(config);
    }

    /**
     * 发送邮件
     *
     * @param id id
     * @param file 文档
     */
    public void sendEmail(Long id, File file) {
        AuthConfig config = this.getByAuthConfigRequired(id);
        String content = "公司：" + config.getTitle() + "\r\n签名方式：hmacSha256 \r\nappId：" + config.getAppId() + "\r\nappSecret：" + config.getAppSecret();
        emailService.sendEmail(config.getEmail(), "第三方接口对接签名配置", content, false, file);
    }

    /**
     * 配置信息
     *
     * @param id id
     * @return 配置信息
     */
    private AuthConfig getByAuthConfigRequired(Long id) {
        AuthConfig config = authConfigRepository.findById(id);
        if (config == null) {
            throw new BusinessException(ErrorCode.AUTH_NOT_EXIST);
        }
        return config;
    }

    /**
     * 生成密钥
     *
     * @param config config
     */
    private String generateSecretKey() {
        return StringUtil.random(64);
    }
}
