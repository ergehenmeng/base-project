package com.eghm.domain.operate.service;

import com.eghm.domain.operate.repository.AuthConfigRepository;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;

/**
 * 授权配置领域服务.
 *
 * @author 二哥很猛
 */
public class AuthConfigDomainService {

    public void assertTitleAvailable(AuthConfigRepository repository, String title, Long excludeId) {
        if (repository.existsByTitle(title, excludeId)) {
            throw new BusinessException(ErrorCode.AUTH_TITLE_REDO);
        }
    }
}
