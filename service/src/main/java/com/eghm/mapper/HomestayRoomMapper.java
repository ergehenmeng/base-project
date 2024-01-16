package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.homestay.room.HomestayRoomQueryDTO;
import com.eghm.dto.business.homestay.room.HomestayRoomQueryRequest;
import com.eghm.model.HomestayRoom;
import com.eghm.vo.business.homestay.room.HomestayRoomListVO;
import com.eghm.vo.business.homestay.room.HomestayRoomResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<HomestayRoomResponse> listPage(Page<HomestayRoomResponse> page, @Param("param") HomestayRoomQueryRequest request);

    /**
     * 分页查询民宿下的房型信息
     *
     * @param page 分页
     * @param dto  查询条件
     * @return 房型列表
     */
    Page<HomestayRoomListVO> getByPage(Page<HomestayRoomListVO> page, @Param("param") HomestayRoomQueryDTO dto);

    /**
     * 查询推荐的房型
     *
     * @param homestayId 民宿id
     * @param limit      查询数量
     * @return 房型列表
     */
    List<HomestayRoomListVO> getRecommendRoom(@Param("homestayId") Long homestayId, @Param("limit") Integer limit);
}
