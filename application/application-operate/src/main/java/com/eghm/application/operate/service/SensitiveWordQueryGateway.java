package com.eghm.application.operate.service;

import com.eghm.dto.ext.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.domain.operate.model.SensitiveWord;

import java.util.List;

/**
 * 敏感词查询端口
 *
 * @author wyb
 * @since 2021/12/4 11:02
 */
public interface SensitiveWordQueryGateway {

    Page<SensitiveWord> getByPage(PagingQuery query, List<String> keywords);
}
