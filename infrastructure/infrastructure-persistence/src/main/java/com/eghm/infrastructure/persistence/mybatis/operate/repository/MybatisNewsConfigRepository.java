package com.eghm.infrastructure.persistence.mybatis.operate.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.infrastructure.persistence.mybatis.mapper.NewsConfigMapper;
import com.eghm.domain.operate.model.NewsConfig;
import com.eghm.domain.operate.repository.NewsConfigRepository;
import com.eghm.infrastructure.persistence.mybatis.po.NewsConfigPO;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.infrastructure.persistence.mybatis.util.MybatisUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 资讯配置 MyBatis 仓储适配器
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Repository
@AllArgsConstructor
public class MybatisNewsConfigRepository implements NewsConfigRepository {

    private final NewsConfigMapper newsConfigMapper;

    @Override
    public void save(NewsConfig newsConfig) {
        newsConfigMapper.insert(toPo(newsConfig));
    }

    @Override
    public void update(NewsConfig newsConfig) {
        newsConfigMapper.updateById(toPo(newsConfig));
    }

    @Override
    public void deleteById(Long id) {
        newsConfigMapper.deleteById(id);
    }

    @Override
    public NewsConfig findByCode(String code) {
        return toDomain(MybatisUtil.getOne(newsConfigMapper, NewsConfigPO::getCode, code));
    }

    @Override
    public boolean existsByTitle(String title, Long excludeId) {
        return exists(NewsConfigPO::getTitle, title, excludeId);
    }

    @Override
    public boolean existsByCode(String code, Long excludeId) {
        return exists(NewsConfigPO::getCode, code, excludeId);
    }

    private boolean exists(com.baomidou.mybatisplus.core.toolkit.support.SFunction<NewsConfigPO, ?> column, Object value, Long excludeId) {
        LambdaQueryWrapper<NewsConfigPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(column, value);
        if (excludeId != null) {
            wrapper.ne(NewsConfigPO::getId, excludeId);
        }
        return newsConfigMapper.selectCount(wrapper) > 0;
    }

    private NewsConfig toDomain(NewsConfigPO newsConfigPO) {
        return DataUtil.copy(newsConfigPO, NewsConfig.class);
    }

    private NewsConfigPO toPo(NewsConfig newsConfig) {
        return DataUtil.copy(newsConfig, NewsConfigPO.class);
    }
}
