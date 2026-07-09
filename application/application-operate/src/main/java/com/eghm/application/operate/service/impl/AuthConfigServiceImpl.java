package com.eghm.application.operate.service.impl;

import cn.hutool.core.util.IdUtil;
import com.eghm.dto.ext.Page;
import com.eghm.common.EmailService;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.operate.auth.AuthConfigAddRequest;
import com.eghm.dto.operate.auth.AuthConfigEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.domain.operate.model.AuthConfig;
import com.eghm.domain.operate.repository.AuthConfigRepository;
import com.eghm.application.operate.service.AuthConfigQueryGateway;
import com.eghm.application.operate.service.AuthConfigService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.StringUtil;
import com.eghm.vo.operate.auth.AuthConfigResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Slf4j
@AllArgsConstructor
@Service("authConfigService")
public class AuthConfigServiceImpl implements AuthConfigService {

    private final EmailService emailService;

    private final AuthConfigRepository authConfigRepository;

    private final AuthConfigQueryGateway authConfigQueryGateway;

    @Override
    public Page<AuthConfigResponse> getByPage(PagingQuery request) {
        return authConfigQueryGateway.getByPage(request);
    }

    @Override
    public void create(AuthConfigAddRequest request) {
        this.assertTitleAvailable(request.getTitle(), null);
        AuthConfig config = DataUtil.copy(request, AuthConfig.class);
        config.initialize(IdUtil.fastSimpleUUID(), this.generateSecretKey(), LocalDate.now());
        authConfigRepository.save(config);
    }

    @Override
    public void update(AuthConfigEditRequest request) {
        this.assertTitleAvailable(request.getTitle(), request.getId());
        AuthConfig config = DataUtil.copy(request, AuthConfig.class);
        authConfigRepository.update(config);
    }

    @Override
    public void deleteById(Long id) {
        authConfigRepository.deleteById(id);
    }

    @Override
    public void reset(Long id) {
        AuthConfig config = this.getByAuthConfigRequired(id);
        config.resetSecret(this.generateSecretKey());
        authConfigRepository.update(config);
    }

    @Override
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

    private void assertTitleAvailable(String title, Long excludeId) {
        if (authConfigRepository.existsByTitle(title, excludeId)) {
            log.warn("第三方授权配置单位名称重复 [{}] [{}]", title, excludeId);
            throw new BusinessException(ErrorCode.AUTH_TITLE_REDO);
        }
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
