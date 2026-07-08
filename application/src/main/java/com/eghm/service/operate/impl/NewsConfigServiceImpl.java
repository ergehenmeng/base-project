package com.eghm.service.operate.impl;

import com.eghm.dto.ext.Page;
import com.eghm.dto.business.news.config.NewsConfigAddRequest;
import com.eghm.dto.business.news.config.NewsConfigEditRequest;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.operate.model.NewsConfig;
import com.eghm.operate.repository.NewsConfigRepository;
import com.eghm.service.operate.NewsConfigQueryGateway;
import com.eghm.service.operate.NewsConfigService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.news.NewsConfigResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class NewsConfigServiceImpl implements NewsConfigService {

    private final NewsConfigRepository newsConfigRepository;

    private final NewsConfigQueryGateway newsConfigQueryGateway;

    @Override
    public Page<NewsConfig> getByPage(PagingQuery query) {
        return newsConfigQueryGateway.getByPage(query);
    }

    @Override
    public List<NewsConfigResponse> getList() {
        return newsConfigQueryGateway.getList();
    }

    @Override
    public void create(NewsConfigAddRequest request) {
        this.assertTitleAvailable(request.getTitle(), null);
        this.assertCodeAvailable(request.getCode(), null);
        NewsConfig newsConfig = DataUtil.copy(request, NewsConfig.class);
        newsConfigRepository.save(newsConfig);
    }

    @Override
    public void update(NewsConfigEditRequest request) {
        this.assertTitleAvailable(request.getTitle(), request.getId());
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

    private void assertTitleAvailable(String title, Long excludeId) {
        if (newsConfigRepository.existsByTitle(title, excludeId)) {
            log.warn("资讯配置标题重复 [{}] [{}]", title, excludeId);
            throw new BusinessException(ErrorCode.NEWS_CONFIG_TITLE_REDO);
        }
    }

    private void assertCodeAvailable(String code, Long excludeId) {
        if (newsConfigRepository.existsByCode(code, excludeId)) {
            log.warn("资讯配置编号重复 [{}] [{}]", code, excludeId);
            throw new BusinessException(ErrorCode.NEWS_CONFIG_CODE_REDO);
        }
    }
}
