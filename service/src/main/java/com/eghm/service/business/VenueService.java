package com.eghm.service.business;

import com.eghm.dto.business.venue.VenueAddRequest;
import com.eghm.dto.business.venue.VenueEditRequest;
import com.eghm.model.Venue;

/**
 * <p>
 * 场馆信息 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
public interface VenueService {

    /**
     * 新增场馆
     *
     * @param request 场馆信息
     */
    void create(VenueAddRequest request);

    /**
     * 更新场馆信息
     *
     * @param request 场馆信息
     */
    void update(VenueEditRequest request);

    /**
     * 删除场馆信息
     *
     * @param id id
     */
    void delete(String id);

    /**
     * 查询场馆信息
     *
     * @param id 场馆ID
     * @return 场馆信息
     */
    Venue selectByIdRequired(Long id);
}
