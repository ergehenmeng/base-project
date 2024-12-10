package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.homestay.room.config.RoomConfigEditRequest;
import com.eghm.dto.business.homestay.room.config.RoomConfigQueryRequest;
import com.eghm.dto.business.homestay.room.config.RoomConfigRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.HomestayRoomConfigMapper;
import com.eghm.mapper.HomestayRoomMapper;
import com.eghm.model.HomestayRoom;
import com.eghm.model.HomestayRoomConfig;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.HomestayRoomConfigService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DateUtil;
import com.eghm.vo.business.homestay.room.config.RoomConfigResponse;
import com.eghm.vo.business.homestay.room.config.RoomConfigVO;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * @author 二哥很猛 2022/6/25
 */
@Service("homestayRoomConfigService")
@AllArgsConstructor
@Slf4j
public class HomestayRoomConfigServiceImpl implements HomestayRoomConfigService {

    private final SysConfigApi sysConfigApi;

    private final CommonService commonService;

    private final HomestayRoomMapper homestayRoomMapper;

    private final HomestayRoomConfigMapper homestayRoomConfigMapper;

    @Override
    public void setup(RoomConfigRequest request) {
        long between = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        commonService.checkMaxDay(ConfigConstant.ROOM_CONFIG_MAX_DAY, between);
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
            config.setHomestayId(request.getHomestayId());
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
        HomestayRoom homestayRoom = homestayRoomMapper.selectById(request.getRoomId());
        if (homestayRoom == null) {
            log.error("民宿房间已删除, homestayId: [{}]", request.getRoomId());
            throw new BusinessException(ErrorCode.HOMESTAY_ROOM_NULL);
        }
        LocalDate month = DateUtil.parseFirstDayOfMonth(request.getMonth());
        List<HomestayRoomConfig> configList = this.getMonthConfig(month, request.getRoomId());
        return DataUtil.paddingMonth(configList, (lineConfig, localDate) -> lineConfig.getConfigDate().equals(localDate), RoomConfigResponse::new, month);
    }

    @Override
    public void update(RoomConfigEditRequest request) {
        HomestayRoomConfig config = this.getConfig(request.getRoomId(), request.getConfigDate());
        if (config == null) {
            config = DataUtil.copy(request, HomestayRoomConfig.class);
            config.setHomestayRoomId(request.getRoomId());
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
    public List<HomestayRoomConfig> getList(Long roomId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<HomestayRoomConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(HomestayRoomConfig::getHomestayRoomId, roomId);
        wrapper.ge(HomestayRoomConfig::getConfigDate, startDate);
        wrapper.lt(HomestayRoomConfig::getConfigDate, endDate);
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
    public Integer getRoomMinPrice(Long roomId) {
        int maxDay = sysConfigApi.getInt(ConfigConstant.HOMESTAY_MAX_RESERVE_DAY, 30);
        LocalDate start = LocalDate.now();
        Integer minPrice = homestayRoomConfigMapper.getRoomMinPrice(roomId, start, start.plusDays(maxDay));
        return minPrice == null ? 0 : minPrice;
    }

    @Override
    public List<RoomConfigVO> getList(Long roomId) {
        int maxDay = sysConfigApi.getInt(ConfigConstant.HOMESTAY_MAX_RESERVE_DAY, 30);
        LocalDate start = LocalDate.now();
        List<HomestayRoomConfig> configList = this.getList(roomId, start, start.plusDays(maxDay));
        List<RoomConfigVO> voList = Lists.newArrayListWithExpectedSize(maxDay);
        for (int i = 0; i < maxDay; i++) {
            LocalDate localDate = start.plusDays(i);
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
    public void deletePrice(Integer keepDay) {
        LocalDate localDate = LocalDate.now().minusDays(keepDay);
        LambdaUpdateWrapper<HomestayRoomConfig> wrapper = Wrappers.lambdaUpdate();
        wrapper.lt(HomestayRoomConfig::getConfigDate, localDate);
        homestayRoomConfigMapper.delete(wrapper);
    }

    /**
     * 获取房间某一天的价格配置信息
     *
     * @param roomId     房间id
     * @param configDate 日期
     * @return 价格库存配置信息
     */
    private HomestayRoomConfig getConfig(Long roomId, LocalDate configDate) {
        LambdaQueryWrapper<HomestayRoomConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(HomestayRoomConfig::getHomestayRoomId, roomId);
        wrapper.eq(HomestayRoomConfig::getConfigDate, configDate);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return homestayRoomConfigMapper.selectOne(wrapper);
    }

    /**
     * 获取房态月配置信息
     *
     * @param month  月份
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
