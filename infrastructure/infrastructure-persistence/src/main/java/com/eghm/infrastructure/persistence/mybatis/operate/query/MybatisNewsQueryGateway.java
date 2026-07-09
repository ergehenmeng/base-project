package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.business.news.NewsQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.NewsMapper;
import com.eghm.application.operate.port.out.NewsQueryGateway;
import com.eghm.application.shared.vo.business.news.NewsResponse;
import com.eghm.application.shared.vo.business.news.NewsVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 资讯 MyBatis 查询适配器
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Repository
@AllArgsConstructor
public class MybatisNewsQueryGateway implements NewsQueryGateway {

    private final NewsMapper newsMapper;

    @Override
    public Page<NewsResponse> listPage(Page<NewsResponse> page, NewsQueryRequest request) {
        return MybatisPageUtil.fromMybatis(newsMapper.listPage(MybatisPageUtil.toMybatis(page), request));
    }

    @Override
    public Page<NewsVO> getByPage(Page<NewsVO> page, String queryName) {
        return MybatisPageUtil.fromMybatis(newsMapper.getByPage(MybatisPageUtil.toMybatis(page), queryName));
    }
}





