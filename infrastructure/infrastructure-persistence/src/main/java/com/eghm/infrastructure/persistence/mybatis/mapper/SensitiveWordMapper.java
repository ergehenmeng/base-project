package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SensitiveWordPO;

import java.util.List;

/**
 * <p>
 * 评论敏感词库 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2021-12-04
 */
public interface SensitiveWordMapper extends BaseMapper<SensitiveWordPO> {

    /**
     * 查询所有敏感词
     *
     * @return 所有
     */
    List<String> getWordList();

}

