package com.eghm.service.business.impl;

import com.eghm.dao.mapper.HomestayRoomMapper;
import com.eghm.service.business.HomestayRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author wyb 2022/6/25 14:10
 */
@Service("homestayRoomService")
@AllArgsConstructor
public class HomestayRoomServiceImpl implements HomestayRoomService {

    private final HomestayRoomMapper homestayRoomMapper;


}
