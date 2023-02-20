package com.eghm.service.business;

import com.eghm.model.dto.business.travel.TravelAgencyAddRequest;

/**
 * @author 殿小二
 * @date 2023/2/18
 */
public interface TravelAgencyService {
    
    /**
     * 新增旅行社
     * @param request 旅行社信息
     */
    void create(TravelAgencyAddRequest request);
}
