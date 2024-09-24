package com.eghm.service.common.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.ClearCacheService;
import com.eghm.dto.auth.AuthConfigAddRequest;
import com.eghm.dto.auth.AuthConfigEditRequest;
import com.eghm.dto.auth.AuthConfigQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.SignType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.AuthConfigMapper;
import com.eghm.model.AuthConfig;
import com.eghm.service.common.AuthConfigService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.StringUtil;
import com.eghm.vo.auth.AuthConfigResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Slf4j
@AllArgsConstructor
@Service("authConfigService")
public class AuthConfigServiceImpl implements AuthConfigService {

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
        if (request.getSignType() == SignType.RSA) {
            RSA rsa = SecureUtil.rsa();
            config.setPublicKey(rsa.getPublicKeyBase64());
            config.setPrivateKey(rsa.getPrivateKeyBase64());
        } else {
            config.setPrivateKey(StringUtil.random(64));
        }
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
