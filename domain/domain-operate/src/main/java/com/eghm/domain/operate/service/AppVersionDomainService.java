package com.eghm.domain.operate.service;

import com.eghm.domain.operate.repository.AppVersionRepository;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;

/**
 * App版本领域服务.
 *
 * @author 二哥很猛
 */
public class AppVersionDomainService {

    public void assertVersionAvailable(AppVersionRepository repository, String version) {
        if (repository.existsByVersion(version)) {
            throw new BusinessException(ErrorCode.VERSION_REDO);
        }
    }
}
