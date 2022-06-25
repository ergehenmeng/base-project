package com.eghm.service.business.impl;

import com.eghm.dao.mapper.HomestayRoomConfigMapper;
import com.eghm.service.business.HomestayRoomConfigService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wyb 2022/6/25 14:11
 */
@Service("homestayRoomConfigService")
@AllArgsConstructor
public class HomestayRoomConfigServiceImpl implements HomestayRoomConfigService {

    private final HomestayRoomConfigMapper homestayRoomConfigMapper;

}
