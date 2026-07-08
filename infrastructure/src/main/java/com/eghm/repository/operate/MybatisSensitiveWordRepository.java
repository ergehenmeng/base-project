package com.eghm.repository.operate;

import com.eghm.mapper.SensitiveWordMapper;
import com.eghm.operate.model.SensitiveWord;
import com.eghm.operate.repository.SensitiveWordRepository;
import com.eghm.po.SensitiveWordPO;
import com.eghm.utils.DataUtil;
import com.eghm.utils.MybatisUtil;
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
