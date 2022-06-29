package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.eghm.dao.mapper.HomestayRoomConfigMapper;
import com.eghm.dao.model.HomestayRoom;
import com.eghm.dao.model.HomestayRoomConfig;
import com.eghm.model.dto.homestay.room.config.RoomConfigRequest;
import com.eghm.service.business.HomestayRoomConfigService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author 二哥很猛 2022/6/25 14:11
 */
@Service("homestayRoomConfigService")
@AllArgsConstructor
public class HomestayRoomConfigServiceImpl implements HomestayRoomConfigService {

    private final HomestayRoomConfigMapper homestayRoomConfigMapper;

    @Override
    public void setup(RoomConfigRequest request) {
        long between = ChronoUnit.DAYS.between(request.getStartDate(), request.getStartDate());
        HomestayRoomConfig config;
        List<Integer> week = request.getWeek();
        for (int i = 0; i <= between; i++) {
            LocalDate localDate = request.getStartDate().plusDays(i);
            boolean contains = week.contains(localDate.getDayOfWeek().getValue());
            // 只有该日期所在周日期在指定范围时,才进行插入或更新操作
            if (contains) {
                for (Long roomId : request.getRoomIds()) {
                    config = new HomestayRoomConfig();
                    config.setId(IdWorker.getId());
                    config.setConfigDate(localDate);
                    config.setHomestayRoomId(roomId);
                    config.setState(request.getState());
                    config.setStock(request.getStock());
                    config.setLinePrice(request.getLinePrice());
                    config.setSalePrice(request.getSalePrice());
                    homestayRoomConfigMapper.insertOrUpdate(config);
                }
            }
        }
    }



}
