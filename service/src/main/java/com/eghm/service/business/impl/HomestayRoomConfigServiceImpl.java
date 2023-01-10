package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.DateUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.HomestayRoomConfigMapper;
import com.eghm.model.HomestayRoom;
import com.eghm.model.HomestayRoomConfig;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigEditRequest;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigQueryRequest;
import com.eghm.model.dto.business.homestay.room.config.RoomConfigRequest;
import com.eghm.model.vo.business.homestay.room.config.HomestayMinPriceVO;
import com.eghm.model.vo.business.homestay.room.config.RoomConfigResponse;
import com.eghm.model.vo.business.homestay.room.config.RoomConfigVO;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.HomestayRoomConfigService;
import com.eghm.service.business.HomestayRoomService;
import com.eghm.utils.DataUtil;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛 2022/6/25
 */
@Service("homestayRoomConfigService")
@AllArgsConstructor
@Slf4j
public class HomestayRoomConfigServiceImpl implements HomestayRoomConfigService {

    private final HomestayRoomConfigMapper homestayRoomConfigMapper;

    private final CommonService commonService;

    private final HomestayRoomService homestayRoomService;

    @Override
    public void setup(RoomConfigRequest request) {
        long between = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());

        commonService.checkMaxDay(ConfigConstant.ROOM_CONFIG_MAX_DAY, between);

        HomestayRoom room = homestayRoomService.selectByIdRequired(request.getRoomId());


        HomestayRoomConfig config;
        List<Integer> week = request.getWeek();
        for (int i = 0; i <= between; i++) {
            LocalDate localDate = request.getStartDate().plusDays(i);
            if (!week.contains(localDate.getDayOfWeek().getValue())) {
                continue;
            }
            // 只有该日期所在周日期在指定范围时,才进行插入或更新操作
            config = new HomestayRoomConfig();
            config.setId(IdWorker.getId());
            config.setHomestayId(room.getHomestayId());
            config.setConfigDate(localDate);
            config.setHomestayRoomId(request.getRoomId());
            config.setState(request.getState());
            config.setStock(request.getStock());
            config.setLinePrice(request.getLinePrice());
            config.setSalePrice(request.getSalePrice());
            homestayRoomConfigMapper.insertOrUpdate(config);
        }
    }

    @Override
    public List<RoomConfigResponse> getList(RoomConfigQueryRequest request) {
        LocalDate month = DateUtil.parseFirstDayOfMonth(request.getMonth());
        List<HomestayRoomConfig> configList = this.getMonthConfig(month, request.getRoomId());
        return DataUtil.paddingMonth(configList, (lineConfig, localDate) -> lineConfig.getConfigDate().equals(localDate), RoomConfigResponse::new, month);
    }

    @Override
    public void update(RoomConfigEditRequest request) {
        HomestayRoomConfig config = this.getConfig(request.getRoomId(), request.getConfigDate());
        if (config == null) {
            config = DataUtil.copy(request, HomestayRoomConfig.class);
            homestayRoomConfigMapper.insert(config);
        } else {
            config.setLinePrice(request.getLinePrice());
            config.setSalePrice(request.getSalePrice());
            config.setStock(request.getStock());
            config.setState(request.getState());
            homestayRoomConfigMapper.updateById(config);
        }
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

    @Override
    public HomestayRoomConfig getConfig(Long roomId, LocalDate configDate) {
        LambdaQueryWrapper<HomestayRoomConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(HomestayRoomConfig::getHomestayRoomId, roomId);
        wrapper.eq(HomestayRoomConfig::getConfigDate, configDate);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return homestayRoomConfigMapper.selectOne(wrapper);
    }

    @Override
    public List<HomestayRoomConfig> getList(Long roomId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<HomestayRoomConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(HomestayRoomConfig::getHomestayRoomId, roomId);
        wrapper.ge(HomestayRoomConfig::getConfigDate, startDate);
        wrapper.le(HomestayRoomConfig::getConfigDate, endDate);
        wrapper.last(" order by config_date asc ");
        return homestayRoomConfigMapper.selectList(wrapper);
    }

    @Override
    public void updateStock(Long roomId, LocalDate startDate, LocalDate endDate, Integer num) {
        int stock = homestayRoomConfigMapper.updateStock(roomId, startDate, endDate, num);
        long size = ChronoUnit.DAYS.between(startDate, endDate);
        if (stock < size) {
            log.error("更新房态库存时,存在库存不足 [{}] [{}] [{}]", roomId, stock, size);
            throw new BusinessException(ErrorCode.HOMESTAY_STOCK);
        }
    }

    @Override
    public Map<Long, Integer> getHomestayMinPrice(List<Long> homestayList, LocalDate startDate, LocalDate endDate) {
        if (CollUtil.isEmpty(homestayList)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        List<HomestayMinPriceVO> priceList = homestayRoomConfigMapper.getHomestayMinPrice(homestayList, startDate, endDate);

        if (CollUtil.isEmpty(priceList)) {
            return Maps.newLinkedHashMapWithExpectedSize(4);
        }
        return priceList.stream().collect(Collectors.toMap(HomestayMinPriceVO::getHomestayId, HomestayMinPriceVO::getMinPrice, (integer, integer2) -> integer));
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
