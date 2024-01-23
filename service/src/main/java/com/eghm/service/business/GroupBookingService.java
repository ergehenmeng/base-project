package com.eghm.service.business;

import com.eghm.dto.business.group.GroupBookingAddRequest;
import com.eghm.dto.business.group.GroupBookingEditRequest;

/**
 * <p>
 * 拼团活动表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-23
 */
public interface GroupBookingService {

    /**
     * 新增拼团活动
     *
     * @param request 拼团信息
     */
    void create(GroupBookingAddRequest request);

    /**
     * 修改拼团活动
     *
     * @param request 拼团信息
     */
    void update(GroupBookingEditRequest request);
}
