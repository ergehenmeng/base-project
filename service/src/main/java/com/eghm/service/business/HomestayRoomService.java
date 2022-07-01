package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.HomestayRoom;
import com.eghm.model.dto.business.homestay.room.HomestayRoomAddRequest;
import com.eghm.model.dto.business.homestay.room.HomestayRoomEditRequest;
import com.eghm.model.dto.business.homestay.room.HomestayRoomQueryRequest;

/**
 * @author 二哥很猛 2022/6/25
 */
public interface HomestayRoomService {

    /**
     * 分页查询房型信息
     * @param request 查询条件
     * @return 房型信息
     */
    Page<HomestayRoom> getByPage(HomestayRoomQueryRequest request);

    /**
     * 新增房型
     * @param request 房型信息
     */
    void create(HomestayRoomAddRequest request);

    /**
     * 更新房型
     * @param request 房型信息
     */
    void update(HomestayRoomEditRequest request);

    /**
     * 主键查询房型
     * @param id id
     * @return 房型信息
     */
    HomestayRoom selectById(Long id);

    /**
     * 更新房型
     * @param id 房型id
     * @param state 新状态 0:下架 1:上架
     */
    void updateState(Long id, Integer state);
}
