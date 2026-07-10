package com.eghm.application.operate.service;

import com.eghm.application.shared.dto.operate.help.HelpAddRequest;
import com.eghm.application.shared.dto.operate.help.HelpEditRequest;
import com.eghm.domain.operate.model.HelpCenter;

/**
 * @author 帮助说明
 * @since 2018/11/20 20:20
 */
public interface HelpCenterApplicationService {

    /**
     * 添加帮助说明
     *
     * @param request 前台参数
     */
    void create(HelpAddRequest request);

    /**
     * 更新帮助说明
     *
     * @param request 前台参数
     */
    void update(HelpEditRequest request);

    /**
     * 排序
     *
     * @param id     id
     * @param sortBy 排序 最大999
     */
    void sortBy(Long id, Integer sortBy);

    /**
     * 删除帮助说明
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 根据id查询
     *
     * @param id id
     * @return 帮助说明
     */
    HelpCenter selectById(Long id);

}

