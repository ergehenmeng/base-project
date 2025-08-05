package com.eghm.service.operate.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.ClearCacheService;
import com.eghm.common.EmailService;
import com.eghm.dto.operate.auth.AuthConfigAddRequest;
import com.eghm.dto.operate.auth.AuthConfigEditRequest;
import com.eghm.dto.operate.auth.AuthConfigQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.SignType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.AuthConfigMapper;
import com.eghm.model.AuthConfig;
import com.eghm.service.operate.AuthConfigService;
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

    private final AuthConfigMapper authConfigMapper;

    private final ClearCacheService clearCacheService;

    @Override
    public Page<AuthConfigResponse> getByPage(AuthConfigQueryRequest request) {
        return authConfigMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(AuthConfigAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        AuthConfig config = DataUtil.copy(request, AuthConfig.class);
        config.setAppKey(IdUtil.fastSimpleUUID());
        this.generateSecretKey(config);
        // 不填,默认有效期一年
        if (config.getExpireDate() == null) {
            config.setExpireDate(LocalDate.now().plusYears(1));
        }
        authConfigMapper.insert(config);
        clearCacheService.clearAuthConfig();
    }

    @Override
    public void update(AuthConfigEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        AuthConfig config = DataUtil.copy(request, AuthConfig.class);
        authConfigMapper.updateById(config);
        clearCacheService.clearAuthConfig();
    }

    @Override
    public void deleteById(Long id) {
        authConfigMapper.deleteById(id);
        clearCacheService.clearAuthConfig();
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
        String content = "公司：" + config.getTitle() + "\r\n签名方式：" + config.getSignType().name() + "\r\nappKey：" + config.getAppKey() + "\r\nsecret：" + config.getPrivateKey();
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
     * @param config config
     */
    private void generateSecretKey(AuthConfig config) {
        if (config.getSignType() == SignType.RSA) {
            RSA rsa = SecureUtil.rsa();
            config.setPublicKey(rsa.getPublicKeyBase64());
            config.setPrivateKey(rsa.getPrivateKeyBase64());
        } else {
            config.setPrivateKey(StringUtil.random(64));
        }
    }

    /**
     * 校验名称是否重复
     *
     * @param title 名称
     * @param id    id 编辑时不能为空
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<AuthConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AuthConfig::getTitle, title);
        wrapper.ne(id != null, AuthConfig::getId, id);
        Long count = authConfigMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("第三方授权配置检测到单位名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.AUTH_TITLE_REDO);
        }
    }

}
