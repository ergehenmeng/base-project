package com.eghm.domain.operate.service;

import com.eghm.domain.operate.repository.NewsConfigRepository;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;

/**
 * 资讯配置领域服务.
 *
 * @author 二哥很猛
 */
public class NewsConfigDomainService {

    public void assertTitleAvailable(NewsConfigRepository repository, String title, Long excludeId) {
        if (repository.existsByTitle(title, excludeId)) {
            throw new BusinessException(ErrorCode.NEWS_CONFIG_TITLE_REDO);
        }
    }

    public void assertCodeAvailable(NewsConfigRepository repository, String code, Long excludeId) {
        if (repository.existsByCode(code, excludeId)) {
            throw new BusinessException(ErrorCode.NEWS_CONFIG_CODE_REDO);
        }
    }
}
