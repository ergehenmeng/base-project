package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.HomestayRoom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.dto.business.homestay.room.HomestayRoomQueryRequest;
import com.eghm.model.vo.business.homestay.room.HomestayRoomResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 房型信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-25
 */
public interface HomestayRoomMapper extends BaseMapper<HomestayRoom> {

    /**
     * 分页查询酒店房型信息
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<HomestayRoomResponse> listPage(Page<HomestayRoomResponse> page, @Param("request") HomestayRoomQueryRequest request);
}
