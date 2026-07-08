package com.eghm.service.operate;

import com.eghm.dto.ext.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.operate.model.SensitiveWord;

/**
 * @author wyb
 * @since 2021/12/4 11:02
 */
public interface SensitiveWordService {

    /**
     * 分页查询敏感词信息
     *
     * @param query 查询条件
     * @return 列表
     */
    Page<SensitiveWord> getByPage(PagingQuery query);

    /**
     * 重新加载敏感词
     * @param sync 同步给其他服务 true:同步 false:不同步
     */
    void reloadLexicon(boolean sync);

    /**
     * 添加敏感词
     *
     * @param keyword 敏感词
     */
    void create(String keyword);

    /**
     * 删除敏感词
     *
     * @param id 敏感词id
     */
    void delete(Long id);
}
