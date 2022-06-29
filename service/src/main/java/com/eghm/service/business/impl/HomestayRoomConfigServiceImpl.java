package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dao.mapper.HomestayRoomConfigMapper;
import com.eghm.dao.model.HomestayRoomConfig;
import com.eghm.model.dto.homestay.room.config.RoomConfigQueryRequest;
import com.eghm.model.dto.homestay.room.config.RoomConfigRequest;
import com.eghm.model.vo.homestay.room.RoomConfigResponse;
import com.eghm.service.business.HomestayRoomConfigService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<RoomConfigResponse> getList(RoomConfigQueryRequest request) {
        LambdaQueryWrapper<HomestayRoomConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(HomestayRoomConfig::getHomestayRoomId, request.getRoomId());
        LocalDate endDate = request.getMonth().plusMonths(1);
        wrapper.ge(HomestayRoomConfig::getConfigDate, request.getMonth());
        wrapper.lt(HomestayRoomConfig::getConfigDate, endDate);

        List<HomestayRoomConfig> configList = homestayRoomConfigMapper.selectList(wrapper);

        int ofMonth = request.getMonth().getDayOfMonth();
        List<RoomConfigResponse> responseList = new ArrayList<>(45);
        for (int i = 0; i < ofMonth; i++) {
            LocalDate localDate = request.getMonth().plusMonths(i);
            Optional<HomestayRoomConfig> optional = configList.stream().filter(config -> config.getConfigDate().isEqual(localDate)).findFirst();
            // 当天已经设置过金额
            if (optional.isPresent()) {
                RoomConfigResponse response = DataUtil.copy(optional.get(), RoomConfigResponse.class);
                response.setHasSet(true);
                responseList.add(response);
            } else {
                // 当天没有设置过
                responseList.add(new RoomConfigResponse(false));
            }
        }
        
        return responseList;
    }

}
