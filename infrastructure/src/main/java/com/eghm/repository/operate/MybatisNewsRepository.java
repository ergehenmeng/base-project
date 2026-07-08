package com.eghm.repository.operate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.NewsMapper;
import com.eghm.operate.model.News;
import com.eghm.operate.repository.NewsRepository;
import com.eghm.po.NewsPO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 资讯 MyBatis 仓储适配器
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Repository
@AllArgsConstructor
public class MybatisNewsRepository implements NewsRepository {

    private final NewsMapper newsMapper;

    @Override
    public News findById(Long id) {
        return toDomain(newsMapper.selectById(id));
    }

    @Override
    public void save(News news) {
        newsMapper.insert(toPo(news));
    }

    @Override
    public void update(News news) {
        newsMapper.updateById(toPo(news));
    }

    @Override
    public void deleteById(Long id) {
        newsMapper.deleteById(id);
    }

    @Override
    public boolean existsByTitleAndCode(String title, String code, Long excludeId) {
        LambdaQueryWrapper<NewsPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(NewsPO::getTitle, title);
        wrapper.eq(NewsPO::getCode, code);
        if (excludeId != null) {
            wrapper.ne(NewsPO::getId, excludeId);
        }
        return newsMapper.selectCount(wrapper) > 0;
    }

    @Override
    public void updateState(Long id, Boolean state) {
        LambdaUpdateWrapper<NewsPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(NewsPO::getId, id);
        wrapper.set(NewsPO::getState, state);
        newsMapper.update(null, wrapper);
    }

    @Override
    public void updateSort(Long id, Integer sortBy) {
        LambdaUpdateWrapper<NewsPO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(NewsPO::getId, id);
        wrapper.set(NewsPO::getSort, sortBy);
        newsMapper.update(null, wrapper);
    }

    @Override
    public void updatePraiseNum(Long id, Integer delta) {
        newsMapper.updatePraiseNum(id, delta);
    }

    private News toDomain(NewsPO newsPO) {
        return DataUtil.copy(newsPO, News.class);
    }

    private NewsPO toPo(News news) {
        return DataUtil.copy(news, NewsPO.class);
    }
}
