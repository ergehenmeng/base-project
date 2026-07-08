package com.eghm.query.operate;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.business.news.NewsQueryRequest;
import com.eghm.mapper.NewsMapper;
import com.eghm.service.operate.NewsQueryGateway;
import com.eghm.vo.business.news.NewsResponse;
import com.eghm.vo.business.news.NewsVO;
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





