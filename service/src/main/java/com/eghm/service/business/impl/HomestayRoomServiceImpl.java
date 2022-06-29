package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.mapper.HomestayRoomMapper;
import com.eghm.dao.model.HomestayRoom;
import com.eghm.model.dto.homestay.room.HomestayRoomAddRequest;
import com.eghm.model.dto.homestay.room.HomestayRoomEditRequest;
import com.eghm.model.dto.homestay.room.HomestayRoomQueryRequest;
import com.eghm.service.business.HomestayRoomService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛 2022/6/25 14:10
 */
@Service("homestayRoomService")
@AllArgsConstructor
public class HomestayRoomServiceImpl implements HomestayRoomService {

    private final HomestayRoomMapper homestayRoomMapper;

    @Override
    public Page<HomestayRoom> getByPage(HomestayRoomQueryRequest request) {
        LambdaQueryWrapper<HomestayRoom> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, HomestayRoom::getState, request.getState());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), HomestayRoom::getTitle, request.getQueryName());
        return homestayRoomMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(HomestayRoomAddRequest request) {
        HomestayRoom room = DataUtil.copy(request, HomestayRoom.class);
        homestayRoomMapper.insert(room);
    }

    @Override
    public void update(HomestayRoomEditRequest request) {
        HomestayRoom room = DataUtil.copy(request, HomestayRoom.class);
        homestayRoomMapper.updateById(room);
    }

    @Override
    public HomestayRoom selectById(Long id) {
        return homestayRoomMapper.selectById(id);
    }

    @Override
    public void updateState(Long id, Integer state) {
        LambdaUpdateWrapper<HomestayRoom> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(HomestayRoom::getId, id);
        wrapper.set(HomestayRoom::getState, state);
        homestayRoomMapper.update(null, wrapper);
    }
}
