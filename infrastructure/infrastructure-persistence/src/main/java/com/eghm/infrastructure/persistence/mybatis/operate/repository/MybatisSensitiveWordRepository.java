package com.eghm.infrastructure.persistence.mybatis.operate.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.SensitiveWordMapper;
import com.eghm.domain.operate.model.SensitiveWord;
import com.eghm.domain.operate.repository.SensitiveWordRepository;
import com.eghm.infrastructure.persistence.mybatis.po.SensitiveWordPO;
import com.eghm.utils.DataUtil;
import com.eghm.infrastructure.persistence.mybatis.util.MybatisUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MybatisSensitiveWordRepository implements SensitiveWordRepository {

    private final SensitiveWordMapper sensitiveWordMapper;

    @Override
    public List<String> listWords() {
        return sensitiveWordMapper.getWordList();
    }

    @Override
    public List<SensitiveWord> findByKeyword(String keyword) {
        return DataUtil.copy(MybatisUtil.getList(sensitiveWordMapper, SensitiveWordPO::getKeyword, keyword), SensitiveWord.class);
    }

    @Override
    public void save(SensitiveWord word) {
        sensitiveWordMapper.insert(DataUtil.copy(word, SensitiveWordPO.class));
    }

    @Override
    public void deleteById(Long id) {
        sensitiveWordMapper.deleteById(id);
    }
}
