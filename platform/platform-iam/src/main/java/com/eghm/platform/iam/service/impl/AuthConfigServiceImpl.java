package com.eghm.platform.iam.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.integration.messaging.service.EmailService;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.platform.iam.dto.AuthConfigAddRequest;
import com.eghm.platform.iam.dto.AuthConfigEditRequest;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.platform.iam.mapper.AuthConfigMapper;
import com.eghm.platform.iam.entity.AuthConfig;
import com.eghm.platform.iam.service.AuthConfigService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.foundation.core.utils.StringUtil;
import com.eghm.foundation.web.utility.ValidationUtil;
import com.eghm.platform.iam.vo.AuthConfigResponse;
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

    private final AuthConfigMapper authConfigMapper;

    @Override
    public Page<AuthConfigResponse> getByPage(PagingQuery request) {
        return authConfigMapper.getByPage(request.createPage(), request.getQueryName());
    }

    @Override
    public void create(AuthConfigAddRequest request) {
        ValidationUtil.redoCheck(authConfigMapper, AuthConfig::getTitle, request.getTitle(), null, null, ErrorCode.AUTH_TITLE_REDO, "第三方授权配置单位名称重复 [{}] [{}]");
        AuthConfig config = DataUtil.copy(request, AuthConfig.class);
        config.setAppId(IdUtil.fastSimpleUUID());
        this.generateSecretKey(config);
        // 不填,默认有效期一年
        if (config.getExpireDate() == null) {
            config.setExpireDate(LocalDate.now().plusYears(1));
        }
        authConfigMapper.insert(config);
    }

    @Override
    public void update(AuthConfigEditRequest request) {
        ValidationUtil.redoCheck(authConfigMapper, AuthConfig::getTitle, request.getTitle(), AuthConfig::getId, request.getId(), ErrorCode.AUTH_TITLE_REDO, "第三方授权配置单位名称重复 [{}] [{}]");
        DataUtil.copy(request, AuthConfig.class, authConfigMapper::updateById);
    }

    @Override
    public void deleteById(Long id) {
        authConfigMapper.deleteById(id);
    }

    @Override
    public void reset(Long id) {
        AuthConfig config = this.getByAuthConfigRequired(id);
        this.generateSecretKey(config);
        authConfigMapper.updateById(config);
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
     * @param id    id
     * @return 配置信息
     */
    private AuthConfig getByAuthConfigRequired(Long id) {
        AuthConfig config = authConfigMapper.selectById(id);
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
    private void generateSecretKey(AuthConfig config) {
        config.setAppSecret(StringUtil.random(64));
    }

}
