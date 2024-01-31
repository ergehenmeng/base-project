package com.eghm.service.business;

import com.eghm.dto.business.venue.VenueSessionAddRequest;
import com.eghm.dto.business.venue.VenueSessionEditRequest;

/**
 * <p>
 * 场馆场次价格信息表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
public interface VenueSessionService {

    /**
     * 新增场次信息
     *
     * @param request 新增信息
     */
    void create(VenueSessionAddRequest request);

    /**
     * 修改场次信息
     *
     * @param request 场次信息
     */
    void update(VenueSessionEditRequest request);

    /**
     * 删除场次信息
     *
     * @param id 场次id
     */
    void delete(Long id);
}
