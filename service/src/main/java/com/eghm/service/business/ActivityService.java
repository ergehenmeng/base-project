package com.eghm.service.business;

import com.eghm.dao.model.Activity;
import com.eghm.model.dto.business.activity.ActivityAddRequest;
import com.eghm.model.dto.business.activity.ActivityConfigRequest;
import com.eghm.model.dto.business.activity.ActivityEditRequest;
import com.eghm.model.vo.business.activity.ActivityBaseResponse;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/18
 */
public interface ActivityService {

    /**
     * 创建周期性活动(批量)
     * @param request 活动信息
     */
    void create(ActivityConfigRequest request);

    /**
     * 创建活动(单个活动)
     * @param request 活动信息
     */
    void create(ActivityAddRequest request);

    /**
     * 更新活动信息
     * @param request 活动信息
     */
    void update(ActivityEditRequest request);

    /**
     * 查询某一月的活动信息
     * @param month 月份 yyyy-MM
     * @return 该月的活动信息
     */
    List<ActivityBaseResponse> getMonthActivity(String month);

    /**
     * 主键查询活动
     * @param id id
     * @return 活动
     */
    Activity selectById(Long id);

    /**
     * 删除活动
     * @param id id
     */
    void deleteById(Long id);
}
