package com.eghm.application.operate.service.impl;

import com.eghm.application.shared.dto.business.news.config.NewsConfigAddRequest;
import com.eghm.application.shared.dto.business.news.config.NewsConfigEditRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.operate.model.NewsConfig;
import com.eghm.domain.operate.repository.NewsConfigRepository;
import com.eghm.domain.operate.service.NewsConfigDomainService;
import com.eghm.application.operate.service.NewsConfigApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资讯配置 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Slf4j
@AllArgsConstructor
@Service("newsConfigService")
public class NewsConfigApplicationServiceImpl implements NewsConfigApplicationService {

    private final NewsConfigRepository newsConfigRepository;

    private static final NewsConfigDomainService NEWS_CONFIG_DOMAIN_SERVICE = new NewsConfigDomainService();

    @Override
    public void create(NewsConfigAddRequest request) {
        NEWS_CONFIG_DOMAIN_SERVICE.assertTitleAvailable(newsConfigRepository, request.getTitle(), null);
        NEWS_CONFIG_DOMAIN_SERVICE.assertCodeAvailable(newsConfigRepository, request.getCode(), null);
        NewsConfig newsConfig = DataUtil.copy(request, NewsConfig.class);
        newsConfigRepository.save(newsConfig);
    }

    @Override
    public void update(NewsConfigEditRequest request) {
        NEWS_CONFIG_DOMAIN_SERVICE.assertTitleAvailable(newsConfigRepository, request.getTitle(), request.getId());
        NewsConfig newsConfig = DataUtil.copy(request, NewsConfig.class);
        newsConfigRepository.update(newsConfig);
    }

    @Override
    public void deleteById(Long id) {
        newsConfigRepository.deleteById(id);
    }

    @Override
    public NewsConfig getByCode(String code) {
        NewsConfig config = newsConfigRepository.findByCode(code);
        if (config == null) {
            throw new BusinessException(ErrorCode.NEWS_CONFIG_NOT_EXIST);
        }
        return config;
    }

}
