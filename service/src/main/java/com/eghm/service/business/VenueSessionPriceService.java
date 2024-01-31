package com.eghm.service.business;

import com.eghm.dto.business.venue.SessionPriceRequest;
import com.eghm.model.VenueSession;
import com.eghm.model.VenueSessionPrice;

import java.util.List;

/**
 * <p>
 * 场馆场次价格信息表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
public interface VenueSessionPriceService {

    /**
     * 新增或修改场馆场次价格信息
     *
     * @param priceList 场次价格信息
     * @param session 场次信息
     */
    void insertOrUpdate(List<SessionPriceRequest> priceList, VenueSession session);

    /**
     * 删除场馆场次价格信息
     *
     * @param venueSessionId 场次主键
     */
    void delete(Long venueSessionId);
}
