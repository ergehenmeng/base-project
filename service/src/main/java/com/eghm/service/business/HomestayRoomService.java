package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.homestay.room.HomestayRoomAddRequest;
import com.eghm.dto.business.homestay.room.HomestayRoomEditRequest;
import com.eghm.dto.business.homestay.room.HomestayRoomQueryDTO;
import com.eghm.dto.business.homestay.room.HomestayRoomQueryRequest;
import com.eghm.enums.ref.State;
import com.eghm.model.HomestayRoom;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.homestay.room.HomestayRoomListVO;
import com.eghm.vo.business.homestay.room.HomestayRoomResponse;
import com.eghm.vo.business.homestay.room.HomestayRoomVO;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/25
 */
public interface HomestayRoomService {

    /**
     * 分页查询房型信息
     *
     * @param request 查询条件
     * @return 房型信息
     */
    Page<HomestayRoomResponse> getByPage(HomestayRoomQueryRequest request);

    /**
     * 分页查询房型信息 分页查询
     *
     * @param request 查询条件
     * @return 房型信息
     */
    List<HomestayRoomResponse> getList(HomestayRoomQueryRequest request);

    /**
     * 新增房型
     *
     * @param request 房型信息
     */
    void create(HomestayRoomAddRequest request);

    /**
     * 更新房型
     *
     * @param request 房型信息
     */
    void update(HomestayRoomEditRequest request);

    /**
     * 主键查询房型
     *
     * @param id id
     * @return 房型信息
     */
    HomestayRoom selectById(Long id);

    /**
     * 主键查询房型(为空报错)
     *
     * @param id id
     * @return 房型信息
     */
    HomestayRoom selectByIdRequired(Long id);

    /**
     * 主键查询房型(房型没有上架则报错)
     *
     * @param id id
     * @return 房型信息
     */
    HomestayRoom selectByIdShelve(Long id);

    /**
     * 更新房型上下架状态
     *
     * @param id    房型id
     * @param state 新状态
     */
    void updateState(Long id, State state);

    /**
     * 逻辑删除 (删除的同时进行下架操作)
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 设置房型为推荐房型
     *
     * @param id 房型id
     */
    void setRecommend(Long id);

    /**
     * 分页查询酒店房型列表
     *
     * @param dto 分页信息
     * @return 房型列表
     */
    List<HomestayRoomListVO> listPage(HomestayRoomQueryDTO dto);

    /**
     * 查询民宿推荐房型, 注意如果没有推荐房型,默认查询最新上架房型
     *
     * @param homestayId 民宿id
     * @return 房型列表
     */
    List<HomestayRoomListVO> getRecommendRoom(Long homestayId);

    /**
     * 查询房型详细信息
     *
     * @param roomId 房型id
     * @return 详细信息
     */
    HomestayRoomVO detailById(Long roomId);

    /**
     * 分页查询商品列表
     *
     * @param request 查询条件
     * @return 基础信息
     */
    Page<BaseProductResponse> getProductPage(BaseProductQueryRequest request);
}
