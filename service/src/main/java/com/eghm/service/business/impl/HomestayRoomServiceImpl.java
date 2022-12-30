package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.mapper.HomestayRoomMapper;
import com.eghm.model.HomestayRoom;
import com.eghm.model.dto.business.homestay.room.HomestayRoomAddRequest;
import com.eghm.model.dto.business.homestay.room.HomestayRoomEditRequest;
import com.eghm.model.dto.business.homestay.room.HomestayRoomQueryRequest;
import com.eghm.model.vo.business.homestay.room.HomestayRoomResponse;
import com.eghm.service.business.HomestayRoomService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛 2022/6/25
 */
@Service("homestayRoomService")
@AllArgsConstructor
@Slf4j
public class HomestayRoomServiceImpl implements HomestayRoomService {

    private final HomestayRoomMapper homestayRoomMapper;

    @Override
    public Page<HomestayRoomResponse> getByPage(HomestayRoomQueryRequest request) {
        return homestayRoomMapper.listPage(request.createPage(), request);
    }

    @Override
    public void create(HomestayRoomAddRequest request) {
        this.titleRedo(request.getTitle(), null, request.getHomestayId());
        HomestayRoom room = DataUtil.copy(request, HomestayRoom.class);
        homestayRoomMapper.insert(room);
    }

    @Override
    public void update(HomestayRoomEditRequest request) {
        this.titleRedo(request.getTitle(), request.getId(), request.getHomestayId());
        HomestayRoom room = DataUtil.copy(request, HomestayRoom.class);
        homestayRoomMapper.updateById(room);
    }

    @Override
    public HomestayRoom selectById(Long id) {
        return homestayRoomMapper.selectById(id);
    }

    @Override
    public HomestayRoom selectByIdRequired(Long id) {
        HomestayRoom room = homestayRoomMapper.selectById(id);
        if (room == null) {
            log.info("房型信息查询为空 [{}]", id);
            throw new BusinessException(ErrorCode.HOMESTAY_ROOM_NULL);
        }
        return room;
    }

    @Override
    public HomestayRoom selectByIdShelve(Long id) {
        HomestayRoom room = this.selectByIdRequired(id);
        if (room.getPlatformState() != PlatformState.SHELVE) {
            log.info("房型系统未上架 [{}]", id);
            throw new BusinessException(ErrorCode.HOMESTAY_ROOM_NULL);
        }
        return room;
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<HomestayRoom> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(HomestayRoom::getId, id);
        wrapper.set(HomestayRoom::getState, state);
        homestayRoomMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        LambdaUpdateWrapper<HomestayRoom> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(HomestayRoom::getId, id);
        wrapper.set(HomestayRoom::getPlatformState, state);
        homestayRoomMapper.update(null, wrapper);
    }

    @Override
    public void deleteById(Long id) {
        homestayRoomMapper.deleteById(id);
    }

    /**
     * 同一家民宿 房型名称重复校验
     * @param roomName 房型名称
     * @param id 房型id
     * @param homestayId 民宿id
     */
    private void titleRedo(String roomName, Long id, Long homestayId) {
        LambdaQueryWrapper<HomestayRoom> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(HomestayRoom::getTitle, roomName);
        wrapper.ne(id != null, HomestayRoom::getId, id);
        wrapper.eq(HomestayRoom::getHomestayId, homestayId);
        Integer count = homestayRoomMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("房型名称名称重复 [{}] [{}] [{}]", roomName, id, homestayId);
            throw new BusinessException(ErrorCode.ROOM_TITLE_REDO);
        }
    }
}
