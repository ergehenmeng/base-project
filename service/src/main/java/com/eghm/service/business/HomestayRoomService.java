package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.HomestayRoom;
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
     * 主键查询房型(为空报错)
     * @param id id
     * @return 房型信息
     */
    HomestayRoom selectByIdRequired(Long id);

    /**
     * 主键查询房型(房型没有上架则报错)
     * @param id id
     * @return 房型信息
     */
    HomestayRoom selectByIdShelve(Long id);

    /**
     * 更新房型上下架状态
     * @param id 房型id
     * @param state 新状态
     */
    void updateState(Long id, State state);

    /**
     * 更新审核状态
     * @param id 房型id
     * @param state 新状态
     */
    void updateAuditState(Long id, PlatformState state);
}
