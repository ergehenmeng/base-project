package com.eghm.operate.repository;

import com.eghm.operate.model.SensitiveWord;

import java.util.List;

/**
 * 敏感词仓储接口
 *
 * @author wyb
 * @since 2021/12/4 11:02
 */
public interface SensitiveWordRepository {

    /**
     * 查询所有敏感词
     *
     * @return 所有
     */
    List<String> listWords();

    /**
     * 根据关键词查询
     *
     * @param keyword 敏感词
     * @return 列表
     */
    List<SensitiveWord> findByKeyword(String keyword);

    /**
     * 添加敏感词
     *
     * @param word 敏感词
     */
    void save(SensitiveWord word);

    /**
     * 删除敏感词
     *
     * @param id 敏感词id
     */
    void deleteById(Long id);
}
