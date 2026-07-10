package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.cache.CacheService;
import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.business.news.NewsQueryRequest;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.constants.CacheConstant;
import com.eghm.infrastructure.persistence.mybatis.mapper.NewsMapper;
import com.eghm.application.operate.query.NewsQueryService;
import com.eghm.application.shared.vo.business.news.NewsResponse;
import com.eghm.application.shared.vo.business.news.NewsVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资讯 MyBatis 查询适配器
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Repository
@AllArgsConstructor
public class MybatisNewsQueryService implements NewsQueryService {

    private final CacheService cacheService;

    private final NewsMapper newsMapper;

    @Override
    public Page<NewsResponse> listPage(Page<NewsResponse> page, NewsQueryRequest request) {
        return MybatisPageUtil.fromMybatis(newsMapper.listPage(MybatisPageUtil.toMybatis(page), request));
    }

    @Override
    public Page<NewsVO> getByPage(Page<NewsVO> page, String queryName) {
        return MybatisPageUtil.fromMybatis(newsMapper.getByPage(MybatisPageUtil.toMybatis(page), queryName));
    }

    @Override
    public List<NewsVO> listClientPage(PagingQuery query) {
        Page<NewsVO> byPage = getByPage(query.createPage(false), query.getQueryName());
        List<NewsVO> records = byPage.getRecords();
        records.forEach(newsVO -> newsVO.setHasPraise(hasPraise(newsVO.getId())));
        return records;
    }

    private Boolean hasPraise(Long id) {
        Long memberId = ApiHolder.tryGetMemberId();
        if (memberId == null) {
            return false;
        }
        return cacheService.getHashValue(CacheConstant.NEWS_PRAISE + id, memberId.toString()) != null;
    }
}
