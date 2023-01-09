package com.eghm.service.business;

import com.eghm.model.Activity;
import com.eghm.model.dto.business.activity.ActivityAddRequest;
import com.eghm.model.dto.business.activity.ActivityConfigRequest;
import com.eghm.model.dto.business.activity.ActivityDeleteRequest;
import com.eghm.model.dto.business.activity.ActivityEditRequest;
import com.eghm.model.vo.business.activity.ActivityBaseDTO;
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
     * @param scenicId 景区id 可以为空
     * @return 该月的活动信息
     */
    List<ActivityBaseResponse> getMonthActivity(String month, Long scenicId);

    /**
     * 主键查询活动
     * @param id id
     * @return 活动
     */
    Activity selectById(Long id);

    /**
     * 主键查询活动 如果为空则抛异常
     * @param id 活动id
     * @return 活动详细信息
     */
    Activity selectByIdRequired(Long id);

    /**
     * 删除活动,条件不能全部为空
     * @param request 删除条件
     */
    void delete(ActivityDeleteRequest request);

    /**
     * 查询景区活动列表, 具体查询多少天内的活动,以系统参数控制
     * @param scenicId 景区id
     * @return 活动列表
     */
    List<ActivityBaseDTO> scenicActivityList(Long scenicId);
}
