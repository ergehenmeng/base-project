package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.DateUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.mapper.HomestayRoomConfigMapper;
import com.eghm.dao.model.HomestayRoomConfig;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigEditRequest;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigQueryRequest;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigRequest;
import com.eghm.model.vo.business.homestay.room.config.RoomConfigResponse;
import com.eghm.model.vo.business.homestay.room.config.RoomConfigVO;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.HomestayRoomConfigService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class HomestayRoomConfigServiceImpl implements HomestayRoomConfigService {

    private final HomestayRoomConfigMapper homestayRoomConfigMapper;

    private final CommonService commonService;


    @Override
    public void setup(RoomConfigRequest request) {
        long between = ChronoUnit.DAYS.between(request.getStartDate(), request.getStartDate());

        commonService.checkMaxDay(ConfigConstant.ROOM_CONFIG_MAX_DAY, between);

        HomestayRoomConfig config;
        List<Integer> week = request.getWeek();
        for (int i = 0; i <= between; i++) {
            LocalDate localDate = request.getStartDate().plusDays(i);
            if (!week.contains(localDate.getDayOfWeek().getValue())) {
                continue;
            }
            // 只有该日期所在周日期在指定范围时,才进行插入或更新操作
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

    @Override
    public List<RoomConfigResponse> getList(RoomConfigQueryRequest request) {
        LocalDate month = DateUtil.parseFirstDayOfMonth(request.getMonth());
        List<HomestayRoomConfig> configList = this.getMonthConfig(month, request.getRoomId());
        int ofMonth = month.lengthOfMonth();
        List<RoomConfigResponse> responseList = new ArrayList<>(45);
        // 月初到月末进行拼装
        for (int i = 0; i < ofMonth; i++) {
            LocalDate localDate = month.plusDays(i);
            Optional<HomestayRoomConfig> optional = configList.stream().filter(config -> config.getConfigDate().isEqual(localDate)).findFirst();
            // 当天已经设置过金额
            if (optional.isPresent()) {
                RoomConfigResponse response = DataUtil.copy(optional.get(), RoomConfigResponse.class);
                response.setHasSet(true);
                responseList.add(response);
            } else {
                // 当天没有设置过
                responseList.add(new RoomConfigResponse(false, localDate));
            }
        }
        return responseList;
    }

    @Override
    public void update(RoomConfigEditRequest request) {
        HomestayRoomConfig config = DataUtil.copy(request, HomestayRoomConfig.class);
        homestayRoomConfigMapper.updateById(config);
    }

    @Override
    public List<RoomConfigVO> getList(LocalDate month, Long roomId) {
        List<HomestayRoomConfig> configList = this.getMonthConfig(month, roomId);
        int monthDay = month.lengthOfMonth();
        List<RoomConfigVO> voList = new ArrayList<>(45);
        // 月初到月末进行拼装
        for (int i = 0; i < monthDay; i++) {
            LocalDate localDate = month.plusDays(i);
            Optional<HomestayRoomConfig> optional = configList.stream().filter(config -> config.getConfigDate().isEqual(localDate)).findFirst();
            if (optional.isPresent()) {
                RoomConfigVO vo = DataUtil.copy(optional.get(), RoomConfigVO.class);
                vo.setState(vo.getState() && vo.getStock() > 0);
                voList.add(vo);
            } else {
                voList.add(new RoomConfigVO(false, localDate));
            }
        }
        return voList;
    }

    /**
     * 获取房态月配置信息
     * @param month 月份
     * @param roomId 房型
     * @return 房态
     */
    private List<HomestayRoomConfig> getMonthConfig(LocalDate month, Long roomId) {
        LambdaQueryWrapper<HomestayRoomConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(HomestayRoomConfig::getHomestayRoomId, roomId);
        LocalDate endDate = month.plusMonths(1);
        wrapper.ge(HomestayRoomConfig::getConfigDate, month);
        wrapper.lt(HomestayRoomConfig::getConfigDate, endDate);
        return homestayRoomConfigMapper.selectList(wrapper);
    }

}
