package com.eghm.service.business;

import com.eghm.model.dto.business.activity.ActivityAddRequest;
import com.eghm.model.dto.business.activity.ActivityEditRequest;

/**
 * @author 二哥很猛
 * @date 2022/7/18
 */
public interface ActivityService {

    /**
     * 创建活动(批量)
     * @param request 活动信息
     */
    void create(ActivityAddRequest request);

    /**
     * 更新活动信息
     * @param request 活动信息
     */
    void update(ActivityEditRequest request);
}
