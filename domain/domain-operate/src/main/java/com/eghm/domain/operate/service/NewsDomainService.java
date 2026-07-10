package com.eghm.domain.operate.service;

import com.eghm.domain.operate.model.News;
import com.eghm.domain.operate.repository.NewsRepository;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;

/**
 * 资讯领域服务.
 *
 * @author 二哥很猛
 */
public class NewsDomainService {

    public void assertTitleAvailable(NewsRepository repository, String title, String code, Long excludeId) {
        if (repository.existsByTitleAndCode(title, code, excludeId)) {
            throw new BusinessException(ErrorCode.NEWS_TITLE_REDO);
        }
    }

    public void assertCommentSupport(News news) {
        if (news == null) {
            throw new BusinessException(ErrorCode.NEWS_NULL);
        }
        news.assertCommentSupport();
    }
}
