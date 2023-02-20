package com.eghm.service.business.impl;

import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.mapper.TravelAgencyMapper;
import com.eghm.model.TravelAgency;
import com.eghm.model.dto.business.travel.TravelAgencyAddRequest;
import com.eghm.service.business.TravelAgencyService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2023/2/18
 */
@Service("travelAgencyService")
@Slf4j
@AllArgsConstructor
public class TravelAgencyServiceImpl implements TravelAgencyService {
    
    private TravelAgencyMapper travelAgencyMapper;
    
    @Override
    public void init(Long merchantId) {
        TravelAgency agency = new TravelAgency();
        agency.setMerchantId(merchantId);
        agency.setState(State.INIT);
        agency.setPlatformState(PlatformState.SHELVE);
        travelAgencyMapper.insert(agency);
    }
    
    @Override
    public void create(TravelAgencyAddRequest request) {
        TravelAgency agency = DataUtil.copy(request, TravelAgency.class);
        agency.setState(State.UN_SHELVE);
        agency.setPlatformState(PlatformState.SHELVE);
        travelAgencyMapper.insert(agency);
    }
}
